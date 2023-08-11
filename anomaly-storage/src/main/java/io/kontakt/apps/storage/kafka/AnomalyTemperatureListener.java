package io.kontakt.apps.storage.kafka;

import io.kontak.apps.event.Anomaly;
import io.kontakt.apps.storage.storage.DataStorage;
import org.apache.kafka.streams.kstream.*;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@EnableBinding(ListenerBinding.class)
public class AnomalyTemperatureListener {

    @Resource
    private DataStorage elasticSearchStorage;

    @StreamListener("anomalyDetectorProcessor-in-0")
    public void process(KStream<String, Anomaly> events) {
        events
                .foreach((key, value) -> {
                    elasticSearchStorage.store(value);
                    System.out.println("Anomaly detected: " + value);
                });
    }
}


