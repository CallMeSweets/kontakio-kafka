package io.kontakt.apps.anomaly.analytics.storage.service;

import io.kontakt.apps.anomaly.analytics.storage.model.AnomalyDTO;
import io.kontakt.apps.anomaly.analytics.storage.model.ESAnomaly;
import io.kontakt.apps.anomaly.analytics.storage.model.ESAnomalyToAnomalyDTOMapper;
import io.kontakt.apps.anomaly.analytics.storage.repository.AnomalyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnomalyService {

    @Resource
    private AnomalyRepository anomalyRepository;
    @Resource
    private ESAnomalyToAnomalyDTOMapper mapper;


    public List<AnomalyDTO> findAnomaliesByRoomId(String roomId) {
        return anomalyRepository.findByRoomId(roomId)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public List<AnomalyDTO> findAnomaliesByThermometerId(String thermometerId) {
        return anomalyRepository.findByThermometerId(thermometerId)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public List<AnomalyDTO> findAnomaliesAboveThreshold(double threshold) {
        return anomalyRepository.findAnomaliesAboveThreshold(threshold)
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

}
