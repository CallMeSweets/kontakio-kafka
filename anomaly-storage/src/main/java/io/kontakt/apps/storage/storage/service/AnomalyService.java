package io.kontakt.apps.storage.storage.service;

import io.kontak.apps.event.Anomaly;
import io.kontakt.apps.storage.storage.model.AnomalyToESAnomalyMapper;
import io.kontakt.apps.storage.storage.model.ESAnomaly;
import io.kontakt.apps.storage.storage.repository.AnomalyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AnomalyService {

    @Resource
    private AnomalyRepository anomalyRepository;
    @Resource
    private AnomalyToESAnomalyMapper mapper;

    public ESAnomaly save(Anomaly anomaly) {
        return anomalyRepository.save(mapper.apply(anomaly));
    }

}
