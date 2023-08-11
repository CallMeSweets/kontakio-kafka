package io.kontakt.apps.storage.kafka;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;
//import storage.DataStorage;

@Configuration
public class KafkaConfig {

//    @Bean
//    public KStream<String, Anomaly> anomalyRecorderProcessor(KStream<String, Anomaly> events) {
//        return events;
////        return new AnomalyTemperatureListener();
//    }


}
