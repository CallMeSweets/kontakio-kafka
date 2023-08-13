package io.kontakt.apps.storage.storage.source;

import io.kontak.apps.event.Anomaly;
import io.kontakt.apps.storage.storage.model.AnomalyToESAnomalyMapper;
import io.kontakt.apps.storage.storage.repository.AnomalyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ElasticSearchStorage implements DataStorage {

    @Resource
    private AnomalyRepository anomalyRepository;
    @Resource
    private AnomalyToESAnomalyMapper mapper;

    @Override
    public void store(Anomaly anomaly) {
        anomalyRepository.save(mapper.apply(anomaly));
    }
}
