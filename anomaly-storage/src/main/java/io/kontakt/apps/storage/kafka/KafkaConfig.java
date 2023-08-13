package io.kontakt.apps.storage.kafka;

import io.kontak.apps.event.Anomaly;
import io.kontakt.apps.storage.storage.source.DataStorage;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class KafkaConfig {

    @Bean
    public Consumer<KStream<String, Anomaly>> anomalyRecorderProcessor(DataStorage elasticSearchStorage) {
        return new AnomalyTemperatureListener(elasticSearchStorage);
    }

}
