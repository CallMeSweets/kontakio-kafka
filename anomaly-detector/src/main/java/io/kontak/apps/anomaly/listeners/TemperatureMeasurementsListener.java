package io.kontak.apps.anomaly.listeners;

import io.kontak.apps.anomaly.detectors.AnomalyDetector;
import io.kontak.apps.anomaly.model.AggregatedTemperature;
import io.kontak.apps.anomaly.serdes.SerdesFactory;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.WindowStore;

import java.time.Duration;
import java.util.function.Function;

public class TemperatureMeasurementsListener implements Function<KStream<String, TemperatureReading>, KStream<String, Anomaly>> {

    private final AnomalyDetector anomalyDetector;

    public TemperatureMeasurementsListener(AnomalyDetector anomalyDetector) {
        this.anomalyDetector = anomalyDetector;
    }

    @Override
    public KStream<String, Anomaly> apply(KStream<String, TemperatureReading> events) {
        return applySampleAlgorithm(events);
    }

    private KStream<String, Anomaly> applySampleAlgorithm(KStream<String, TemperatureReading> events) {
        Initializer<AggregatedTemperature> aggregatedTemperatureInitializer = AggregatedTemperature::new;
        Aggregator<String, TemperatureReading, AggregatedTemperature> aggregator = (key, value, aggregate) -> aggregate.update(key, value);

        KGroupedStream<String, TemperatureReading> groupedStream = events
                .groupBy((key, value) -> value.roomId() + "-" + value.thermometerId(),
                        Grouped.with(Serdes.String(), SerdesFactory.temperatureReadingSerde()));


        KTable<String, AggregatedTemperature> aggregateStream = groupedStream
                .aggregate(
                        aggregatedTemperatureInitializer,
                        aggregator,
                        Materialized.<String, AggregatedTemperature, KeyValueStore<Bytes, byte[]>>
                                        as("aggregated-sample2-store")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(SerdesFactory.aggregatedTemperatureSerde())
                );

        return aggregateStream
                .toStream()
                .mapValues((key, aggregatedTemperature) -> anomalyDetector.apply(aggregatedTemperature.temperatures()))
                .filter((s, anomaly) -> anomaly.isPresent())
                .mapValues((s, anomaly) -> anomaly.get())
                .selectKey((s, anomaly) -> anomaly.thermometerId());
    }

    private KStream<String, Anomaly> applyTimePeriodAlgorithm(KStream<String, TemperatureReading> events) {
        Initializer<AggregatedTemperature> aggregatedTemperatureInitializer = AggregatedTemperature::new;
        Aggregator<String, TemperatureReading, AggregatedTemperature> aggregator = (key, value, aggregate) -> aggregate.update(key, value);

        Duration windowSize = Duration.ofSeconds(10);
        TimeWindows timeWindows = TimeWindows.ofSizeWithNoGrace(windowSize);

        TimeWindowedKStream<String, TemperatureReading> windowedKStream = events
                .groupBy((key, value) -> value.roomId() + "-" + value.thermometerId(),
                        Grouped.with(Serdes.String(), SerdesFactory.temperatureReadingSerde()))
                .windowedBy(timeWindows);


        KTable<Windowed<String>, AggregatedTemperature> aggregateStream = windowedKStream
                .aggregate(
                        aggregatedTemperatureInitializer,
                        aggregator,
                        Materialized.<String, AggregatedTemperature, WindowStore<Bytes, byte[]>>
                                        as("aggregated-window-store")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(SerdesFactory.aggregatedTemperatureSerde())
                );

        return aggregateStream
                .toStream()
                .mapValues((key, aggregatedTemperature) -> anomalyDetector.apply(aggregatedTemperature.temperatures()))
                .filter((s, anomaly) -> anomaly.isPresent())
                .mapValues((s, anomaly) -> anomaly.get())
                .selectKey((s, anomaly) -> anomaly.thermometerId());
    }
}


