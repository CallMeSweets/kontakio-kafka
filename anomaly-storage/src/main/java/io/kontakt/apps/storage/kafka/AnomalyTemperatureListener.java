package io.kontakt.apps.storage.kafka;

import io.kontak.apps.event.Anomaly;
import io.kontakt.apps.storage.storage.source.DataStorage;
import org.apache.kafka.streams.kstream.*;

import java.util.function.Consumer;


public class AnomalyTemperatureListener implements Consumer<KStream<String, Anomaly>> {

    private final DataStorage elasticSearchStorage;

    public AnomalyTemperatureListener(DataStorage elasticSearchStorage) {
        this.elasticSearchStorage = elasticSearchStorage;
    }

    @Override
    public void accept(KStream<String, Anomaly> events) {
        events
                .foreach((key, anomaly) -> elasticSearchStorage.store(anomaly));
    }
}


