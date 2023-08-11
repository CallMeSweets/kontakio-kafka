package io.kontakt.apps.anomaly.analytics.storage.service;

import co.elastic.clients.elasticsearch.ml.Anomaly;
import io.kontakt.apps.anomaly.analytics.storage.ESAnomaly;
import io.kontakt.apps.anomaly.analytics.storage.repository.AnomalyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnomalyService {

    @Resource
    private AnomalyRepository anomalyRepository;


    public List<ESAnomaly> findAnomaliesByRoomId(String roomId) {
        return anomalyRepository.findByRoomId(roomId);
    }

    public List<ESAnomaly> findAnomaliesByThermometerId(String thermometerId) {
        return anomalyRepository.findByThermometerId(thermometerId);
    }

    public List<ESAnomaly> findAnomaliesAboveThreshold(double threshold) {
        return anomalyRepository.findAnomaliesAboveThreshold(threshold);
    }

}
