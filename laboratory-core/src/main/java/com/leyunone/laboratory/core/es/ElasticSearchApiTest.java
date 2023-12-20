package com.leyunone.laboratory.core.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/12/20
 */
public class ElasticSearchApiTest {

    public static RestHighLevelClient esClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost("IP",9200,"http")));

    /**
     * 创建索引
     * @throws IOException
     */
    public static void createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("user");
        CreateIndexResponse indexResponse = esClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        boolean acknowledged = indexResponse.isAcknowledged();
        System.out.println("索引创建状态:" + acknowledged);
    }


    /**
     * 索引信息查询
     * @throws IOException
     */
    public static void getIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("user");
        GetIndexResponse getIndexResponse = esClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(getIndexResponse.getAliases());
        System.out.println(getIndexResponse.getMappings());
        System.out.println(getIndexResponse.getSettings());
    }

    /**
     * 删除索引
     * @throws IOException
     */
    public static void deleteIndex() throws IOException {
        DeleteIndexRequest getIndexRequest = new DeleteIndexRequest("user");
        AcknowledgedResponse delete = esClient.indices().delete(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println("索引删除状态:" + delete.isAcknowledged());
    }

    /**
     * 添加数据
     * @throws Exception
     */
    public static void add() throws Exception{
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("1008");
//        User user = new User();
//        user.setName("茅河野人");
//        user.setAge(28);
//        user.setSex("男");
//        user.setSalary(50000);
//
//        String userData = objectMapper.writeValueAsString(user);
        indexRequest.source("123", XContentType.JSON);
        //插入数据
        IndexResponse response = esClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response.getResult());
    }

    /**
     * 修改数据
     * @throws Exception
     */
    public static void update() throws Exception{
        UpdateRequest request = new UpdateRequest();
        request.index("user").id("1008");
        request.doc(XContentType.JSON,"name","茅河野人");
        //插入数据
        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
    }

    /**
     * 删除
     * @throws Exception
     */
    public static void delete() throws Exception{
        DeleteRequest request = new DeleteRequest();
        request.index("user").id("1008");
        //插入数据
        DeleteResponse delete = esClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.getResult());
    }

//    /**
//     * 批量添加
//     * @throws Exception
//     */
//    public static void batchInsert() throws Exception{
//
//        BulkRequest bulkRequest = new BulkRequest();
//
//        User user1 = new User("关羽","男",33,5500);
//        String userData1 = objectMapper.writeValueAsString(user1);
//        IndexRequest indexRequest1 = new IndexRequest().index("user").id("1002").source(userData1, XContentType.JSON);
//
//        bulkRequest.add(indexRequest1);
//
//        User user2 = new User("黄忠","男",50,8000);
//        String userData2 = objectMapper.writeValueAsString(user2);
//        IndexRequest indexRequest2 = new IndexRequest().index("user").id("1003").source(userData2, XContentType.JSON);
//        bulkRequest.add(indexRequest2);
//
//        User user3 = new User("黄忠2","男",49,10000);
//        String userData3 = objectMapper.writeValueAsString(user3);
//        IndexRequest indexRequest3 = new IndexRequest().index("user").id("1004").source(userData3, XContentType.JSON);
//        bulkRequest.add(indexRequest3);
//
//        User user4 = new User("赵云","男",33,12000);
//        String userData4 = objectMapper.writeValueAsString(user4);
//        IndexRequest indexRequest4 = new IndexRequest().index("user").id("1005").source(userData4, XContentType.JSON);
//        bulkRequest.add(indexRequest4);
//
//        User user5 = new User("马超","男",38,20000);
//        String userData5 = objectMapper.writeValueAsString(user5);
//        IndexRequest indexRequest5 = new IndexRequest().index("user").id("1006").source(userData5, XContentType.JSON);
//        bulkRequest.add(indexRequest5);
//
//        User user6 = new User("关羽","男",41,27000);
//        String userData6 = objectMapper.writeValueAsString(user6);
//        IndexRequest indexRequest6 = new IndexRequest().index("user").id("1007").source(userData6, XContentType.JSON);
//        bulkRequest.add(indexRequest6);
//
//        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
//        System.out.println(bulkResponse.status());
//        System.out.println(bulkResponse.getItems());
//    }

    /**
     * 批量删除
     * @throws Exception
     */
    public static void batchDelete() throws Exception{
        BulkRequest bulkRequest = new BulkRequest();
        DeleteRequest indexRequest1 = new DeleteRequest().index("user").id("1002");
        DeleteRequest indexRequest2 = new DeleteRequest().index("user").id("1003");
        DeleteRequest indexRequest3 = new DeleteRequest().index("user").id("1004");
        DeleteRequest indexRequest4 = new DeleteRequest().index("user").id("1005");
        DeleteRequest indexRequest5 = new DeleteRequest().index("user").id("1006");
        DeleteRequest indexRequest6 = new DeleteRequest().index("user").id("1007");

        bulkRequest.add(indexRequest1);
        bulkRequest.add(indexRequest2);
        bulkRequest.add(indexRequest3);
        bulkRequest.add(indexRequest4);
        bulkRequest.add(indexRequest5);
        bulkRequest.add(indexRequest6);

        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());
        System.out.println(bulkResponse.getItems());
    }

    /**
     * 查询某个索引下的所有数据
     * @throws Exception
     */
    public static void searchIndexAll() throws Exception{
        SearchRequest request = new SearchRequest();
        request.indices("user");
        // 索引中的全部数据查询
        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        request.source(query);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for (SearchHit searchHit : hits){
            System.out.println(searchHit.getSourceAsString());
        }
    }

    /**
     * 按条件搜
     * @throws IOException
     */
    public static void queryByCondition() throws IOException {
        SearchRequest request = new SearchRequest();

        TermQueryBuilder ageQueryBuilder = QueryBuilders.termQuery("sex", "女");
        SearchSourceBuilder query = new SearchSourceBuilder().query(ageQueryBuilder);
        request.source(query);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response.getHits().getHits());
//        System.out.println(response.getHits().getTotalHits());
        SearchHits hits = response.getHits();
        for (SearchHit searchHit : hits){
            System.out.println(searchHit.getSourceAsString());
        }
    }

    /**
     * 分页查询
     */
    public static void queryByPage() throws IOException {
        SearchRequest request = new SearchRequest();

        SearchSourceBuilder sourceBuilder = new
                SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        sourceBuilder.from(0).size(3);
        request.source(sourceBuilder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response.getHits().getHits());
//        System.out.println(response.getHits().getTotalHits());
        SearchHits hits = response.getHits();
        for (SearchHit searchHit : hits){
            System.out.println(searchHit.getSourceAsString());
        }
    }
}
