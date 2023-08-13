package io.kontakt.apps.anomaly.analytics.storage.model;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;

@Service
public class ESAnomalyToAnomalyDTOMapper implements Function<ESAnomaly, AnomalyDTO> {
    @Override
    public AnomalyDTO apply(ESAnomaly esAnomaly) {
        return new AnomalyDTO(
                esAnomaly.getTemperature(),
                esAnomaly.getRoomId(),
                esAnomaly.getThermometerId(),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(esAnomaly.getTimestamp()), ZoneId.systemDefault())
        );
    }
}
