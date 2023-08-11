package io.kontak.apps.anomaly.config;

import io.kontak.apps.anomaly.detectors.AnomalyDetector;
import io.kontak.apps.anomaly.listeners.TemperatureMeasurementsListener;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class KafkaConfig {

    @Bean
    public Function<KStream<String, TemperatureReading>, KStream<String, Anomaly>> anomalyDetectorProcessor(AnomalyDetector samplesAnomalyDetector) {
        return new TemperatureMeasurementsListener(samplesAnomalyDetector);
    }

}
