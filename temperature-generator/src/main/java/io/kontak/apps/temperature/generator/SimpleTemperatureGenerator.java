package io.kontak.apps.temperature.generator;

import io.kontak.apps.event.TemperatureReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class SimpleTemperatureGenerator implements TemperatureGenerator {

    Logger logger = LoggerFactory.getLogger(SimpleTemperatureGenerator.class);

    @Value("${generator.temperature.average}")
    private double average;
    @Value("${generator.temperature.threshold}")
    private double threshold;
    @Value("${generator.temperature.anomaly}")
    private double anomaly;
    @Value("${generator.temperature.chance.for.anomaly}")
    private double chanceForAnomaly;
    @Value("${generator.uuid.lower.bound}")
    private int uuidLowerBound;
    @Value("${generator.uuid.upper.bound}")
    private int uuidUpperBound;

    public static final Random random = new Random();

    @Override
    public List<TemperatureReading> generate() {
        List<TemperatureReading> temperatures = List.of(generateSingleReading());
        logger.info("Generated temperatures: " + temperatures);
        return temperatures;
    }

    private TemperatureReading generateSingleReading() {
        return new TemperatureReading(
                generateTemperature(),
                generateUUID(),
                generateUUID(),
                Instant.now()
        );
    }

    private double generateTemperature() {
        if (random.nextDouble() < chanceForAnomaly) {
            double anomalyThreshold = threshold + (random.nextDouble() * 5 + anomaly);
            return average + (random.nextDouble() * 2 - 1) * anomalyThreshold;
        } else {
            return average + (random.nextDouble() * 2 - 1) * threshold;
        }
    }

    private String generateUUID() {
        return String.valueOf(uuidLowerBound + ((long) (random.nextDouble() * (uuidUpperBound - uuidLowerBound + 1))));
    }
}
