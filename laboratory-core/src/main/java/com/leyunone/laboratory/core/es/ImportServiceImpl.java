package com.leyunone.laboratory.core.es;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * 将mysql数据写入Elasticsearch例子
 */
@Component
public class ImportServiceImpl {
 
    private static final Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);
 
    @Autowired
    private RestHighLevelClient client;
 
 
//    @Override
//    public void importDb2Es(ImportDb2Es importDb2Es) {
//        writeMySQLDataToES(importDb2Es.getDbTableName(),importDb2Es.getDbTableName());
//    }


    private void writeMySQLDataToES(String tableName,String esIndeName) {
        BulkProcessor bulkProcessor = getBulkProcessor(client);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            logger.info("start handle data :" + tableName);
            String sql = "select * from " + tableName;
            ps = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            // 根据自己需要设置 fetchSize
            ps.setFetchSize(20);
            rs = ps.executeQuery();
            ResultSetMetaData colData = rs.getMetaData();
            ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
            HashMap<String, String> map = null;
            int count = 0;
            // c 就是列的名字   v 就是列对应的值
            String c = null;
            String v = null;
            while (rs.next()) {
                count++;
                map = new HashMap<String, String>(128);
                for (int i = 1; i < colData.getColumnCount(); i++) {
                    c = colData.getColumnName(i);
                    v = rs.getString(c);
                    map.put(c, v);
                }
                dataList.add(map);
                // 每1万条 写一次   不足的批次的数据 最后一次提交处理
                if (count % 10000 == 0) {
                    logger.info("mysql handle data  number:" + count);
                    // 将数据添加到 bulkProcessor
                    for (HashMap<String, String> hashMap2 : dataList) {
                        bulkProcessor.add(new IndexRequest(esIndeName).source(hashMap2));
                    }
                    // 每提交一次 清空 map 和  dataList
                    map.clear();
                    dataList.clear();
                }
            }
            // 处理 未提交的数据
            for (HashMap<String, String> hashMap2 : dataList) {
                bulkProcessor.add(new IndexRequest(esIndeName).source(hashMap2));
            }
            bulkProcessor.flush();
 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                connection.close();
                boolean terinaFlag = bulkProcessor.awaitClose(150L, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 
    }
 
    private BulkProcessor getBulkProcessor(RestHighLevelClient client) {
 
        BulkProcessor bulkProcessor = null;
        try {
 
            BulkProcessor.Listener listener = new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long executionId, BulkRequest request) {
                    logger.info("Try to insert data number : "
                            + request.numberOfActions());
                }
 
                @Override
                public void afterBulk(long executionId, BulkRequest request,
                                      BulkResponse response) {
                    logger.info("************** Success insert data number : "
                            + request.numberOfActions() + " , id: " + executionId);
                }
 
                @Override
                public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                    logger.error("Bulk is unsuccess : " + failure + ", executionId: " + executionId);
                }
            };
 
            BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer = (request, bulkListener) -> client
                    .bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
 
            BulkProcessor.Builder builder = BulkProcessor.builder(bulkConsumer, listener);
            builder.setBulkActions(5000);
            builder.setBulkSize(new ByteSizeValue(100L, ByteSizeUnit.MB));
            builder.setConcurrentRequests(10);
            builder.setFlushInterval(TimeValue.timeValueSeconds(100L));
            builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3));
            // 注意点：让参数设置生效
            bulkProcessor = builder.build();
 
        } catch (Exception e) {
            e.printStackTrace();
            try {
                bulkProcessor.awaitClose(100L, TimeUnit.SECONDS);
            } catch (Exception e1) {
                logger.error(e1.getMessage());
            }
        }
        return bulkProcessor;
    }
}