package io.kontakt.apps.anomaly.analytics.storage.repository;

import io.kontakt.apps.anomaly.analytics.storage.model.ESAnomaly;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnomalyRepository extends ElasticsearchRepository<ESAnomaly, String> {

    List<ESAnomaly> findByRoomId(String roomId);

    List<ESAnomaly> findByThermometerId(String roomId);

    @Query("{\"range\": {\"temperature\": {\"gte\": \"?0\"}}}")
    List<ESAnomaly> findAnomaliesAboveThreshold(double threshold);

}
