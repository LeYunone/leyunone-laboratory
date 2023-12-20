package com.leyunone.laboratory.core.es;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * :)
 *  # es 服务地址
 * elasticsearch.host=IP
 * # es 服务端口
 * elasticsearch.port=9200
 * # 配置日志级别,开启 debug 日志
 * logging.level.com.congge=debug
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/12/21
 */
public class ElasticSearchSpringApiTest {


    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    //创建索引并增加映射配置
    public void createIndex(){
        //创建索引，系统初始化会自动创建索引
        System.out.println("创建索引");
    }

    public void deleteIndex(){
        //创建索引，系统初始化会自动创建索引
        boolean flg = elasticsearchRestTemplate.deleteIndex(Product.class);
        System.out.println("删除索引 = " + flg);
    }

    @Data
    @Document(indexName = "shopping", shards = 3, replicas = 1)
    public static class Product {
        //必须有 id,这里的 id 是全局唯一的标识，等同于 es 中的"_id"
        @Id
        private Long id;//商品唯一标识

        /**
         * type : 字段数据类型
         * analyzer : 分词器类型
         * index : 是否索引(默认:true)
         * Keyword : 短语,不进行分词
         */
        @Field(type = FieldType.Text, analyzer = "ik_max_word")
        private String title;//商品名称

        @Field(type = FieldType.Keyword)
        private String category;//分类名称

        @Field(type = FieldType.Double)
        private Double price;//商品价格

        @Field(type = FieldType.Keyword, index = false)
        private String images;//图片地址
    }

    @Repository
    public interface ProductDao extends ElasticsearchRepository<Product, Long> {

    }


    @ConfigurationProperties(prefix = "elasticsearch")
    @Configuration
    @Data
    public class EsConfig extends AbstractElasticsearchConfiguration {

        private String host ;
        private Integer port ;

        //重写父类方法
        @Override
        public RestHighLevelClient elasticsearchClient() {
            RestClientBuilder builder = RestClient.builder(new HttpHost(host, port));
            RestHighLevelClient restHighLevelClient = new
                    RestHighLevelClient(builder);
            return restHighLevelClient;
        }
    }


    public abstract class AbstractElasticsearchConfiguration extends ElasticsearchConfigurationSupport {

        //需重写本方法
        public abstract RestHighLevelClient elasticsearchClient();

        @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
        public ElasticsearchOperations elasticsearchOperations(ElasticsearchConverter elasticsearchConverter) {
            return new ElasticsearchRestTemplate(elasticsearchClient(), elasticsearchConverter);
        }
    }
}
