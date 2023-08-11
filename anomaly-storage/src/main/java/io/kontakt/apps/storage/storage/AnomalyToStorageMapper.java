package io.kontakt.apps.storage.storage;

import io.kontak.apps.event.Anomaly;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AnomalyToStorageMapper implements Function<Anomaly, ESAnomaly> {
    @Override
    public ESAnomaly apply(Anomaly anomaly) {
        return new ESAnomaly(anomaly.temperature(), anomaly.roomId(), anomaly.thermometerId(), anomaly.timestamp().toEpochMilli());
    }
}
