package com.leyunone.laboratory.core.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023/12/20
 */
    public class ElasticSearchTest {

    public static void main(String[] args) throws IOException {
        List list = new ArrayList<>();
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("IP",9200,"http"))
        );
        System.out.println("success");
        esClient.close();
    }
}
