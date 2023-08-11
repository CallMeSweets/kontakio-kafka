package io.kontak.apps.anomaly.model;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TemperatureToAnomalyMapper implements Function<TemperatureReading, Anomaly> {
    @Override
    public Anomaly apply(TemperatureReading temperature) {
        return new Anomaly(
                temperature.temperature(),
                temperature.roomId(),
                temperature.thermometerId(),
                temperature.timestamp()
        );
    }
}
