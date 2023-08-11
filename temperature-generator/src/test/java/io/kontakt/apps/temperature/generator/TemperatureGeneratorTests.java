package io.kontakt.apps.temperature.generator;

import io.kontak.apps.event.TemperatureReading;
import io.kontak.apps.temperature.generator.SimpleTemperatureGenerator;
import io.kontak.apps.temperature.generator.TemperatureGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleTemperatureGenerator.class)
public class TemperatureGeneratorTests {

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

    @Resource
    private TemperatureGenerator simpleTemperatureGenerator;

    @Test
    public void properTemperatureGenerationTest() {
        final int index = 0;

        List<TemperatureReading> generate = simpleTemperatureGenerator.generate();
        assertFalse(generate.isEmpty());
        assertNotNull(generate.get(index));

        assertTrue(Integer.parseInt(generate.get(index).roomId()) >= uuidLowerBound);
        assertTrue(Integer.parseInt(generate.get(index).roomId()) <= uuidUpperBound);

        assertTrue(Integer.parseInt(generate.get(index).thermometerId()) >= uuidLowerBound);
        assertTrue(Integer.parseInt(generate.get(index).thermometerId()) <= uuidUpperBound);

        assertTrue(generate.get(index).temperature() >= average - threshold - anomaly);
        assertTrue(generate.get(index).temperature() <= average + threshold + anomaly);

        assertNotNull(generate.get(index).timestamp());
    }
}
