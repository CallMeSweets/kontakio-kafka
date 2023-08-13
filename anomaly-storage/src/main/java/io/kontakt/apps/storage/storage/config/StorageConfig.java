package io.kontakt.apps.storage.storage.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Value("${data.storage.elastic.host}")
    private String elasticHost;
    @Value("${data.storage.elastic.port}")
    private Integer elasticPort;
    @Value("${data.storage.elastic.protocol}")
    private String elasticProtocol;


    @Bean
    public RestHighLevelClient elasticClient() {
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(elasticHost, elasticPort, elasticProtocol)));
    }

}
