package io.kontak.apps.anomaly.serdes;

import io.kontak.apps.anomaly.model.AggregatedTemperature;
import io.kontak.apps.event.TemperatureReading;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class SerdesFactory {

    public static Serde<AggregatedTemperature> aggregatedTemperatureSerde() {
        JsonSerializer<AggregatedTemperature> serializer = new JsonSerializer<>();
        JsonDeserializer<AggregatedTemperature> deserializer = new JsonDeserializer<>(AggregatedTemperature.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<TemperatureReading> temperatureReadingSerde() {
        JsonSerializer<TemperatureReading> serializer = new JsonSerializer<>();
        JsonDeserializer<TemperatureReading> deserializer = new JsonDeserializer<>(TemperatureReading.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

}
