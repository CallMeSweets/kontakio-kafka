package io.kontakt.apps.storage.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.kontak.apps.event.Anomaly;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class ElasticSearchStorage implements DataStorage {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Resource
    private RestHighLevelClient elasticClient;
    @Resource
    private AnomalyToStorageMapper anomalyToStorageMapper;

    @Override
    public void store(Anomaly anomaly) {
        IndexRequest request = new IndexRequest("temperature-anomalies");
        request.routing(anomaly.roomId() + "-" + anomaly.thermometerId());
        try {
            ESAnomaly esAnomaly = anomalyToStorageMapper.apply(anomaly);
            request.source(objectMapper.writeValueAsString(esAnomaly), XContentType.JSON);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try {
            IndexResponse response = elasticClient.index(request, RequestOptions.DEFAULT);
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
