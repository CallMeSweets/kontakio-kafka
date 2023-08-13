package io.kontak.apps.anomaly.model;

import io.kontak.apps.event.TemperatureReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public record AggregatedTemperature(String key, List<TemperatureReading> temperatures) {

    public static final int SAMPLES_NUMBER_TO_HOLD = 10;

    static Logger logger = LoggerFactory.getLogger(AggregatedTemperature.class);

    public AggregatedTemperature() {
        this("", new LinkedList<>());
    }

    public AggregatedTemperature update(String key, TemperatureReading temperatureReading) {
        logger.info("New record key: {}, value: {}", key, temperatureReading);
        if(temperatures.size() >= SAMPLES_NUMBER_TO_HOLD) {
            temperatures.remove(0);
            temperatures.add(temperatureReading);
        } else {
            temperatures.add(temperatureReading);
        }
        AggregatedTemperature aggregatedTemperature = new AggregatedTemperature(key, temperatures);
        logger.info("Aggregated temperatures: {}", temperatureReading);
        return aggregatedTemperature;
    }
}
