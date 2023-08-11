package io.kontakt.apps.anomaly.detector;

import io.kontak.apps.anomaly.detectors.AnomalyDetector;
import io.kontak.apps.anomaly.detectors.SamplesAnomalyDetector;
import io.kontak.apps.anomaly.model.TemperatureToAnomalyMapper;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SamplesAnomalyDetector.class, TemperatureToAnomalyMapper.class})
public class SamplesAnomalyDetectorTest {

    @Resource
    private AnomalyDetector samplesAnomalyDetector;

    @Test
    public void tooSmallNumberOfSamplesNoAnomalyFoundTest() {
        String roomId = "1";
        String thermometerId = "1";

        List<TemperatureReading> temperatureReadings = List.of(
                new TemperatureReading(5.4d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.5d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(20.0d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.1d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.3d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.5d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.2d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(4.9d, roomId, thermometerId, Instant.now())
        );

        Optional<Anomaly> anomalies = samplesAnomalyDetector.apply(temperatureReadings);
        assertFalse(anomalies.isPresent());
    }

    @Test
    public void enoughNumberOfSamplesNoAnomalyFoundTest() {
        String roomId = "1";
        String thermometerId = "1";

        List<TemperatureReading> temperatureReadings = List.of(
                new TemperatureReading(5.0d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.1d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.2d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.3d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.4d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.5d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.2d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.1d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.3d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.5d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.2d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(4.9d, roomId, thermometerId, Instant.now())
        );

        Optional<Anomaly> anomalies = samplesAnomalyDetector.apply(temperatureReadings);
        assertFalse(anomalies.isPresent());
    }

    @Test
    public void enoughNumberOfSamplesAnomalyFoundTest() {
        double anomaly = 20.0d;
        String roomId = "1";
        String thermometerId = "1";
        
        List<TemperatureReading> temperatureReadings = List.of(
                new TemperatureReading(5.0d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.1d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.2d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.3d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.4d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.5d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(anomaly, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.1d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.3d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.5d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(5.2d, roomId, thermometerId, Instant.now()),
                new TemperatureReading(4.9d, roomId, thermometerId, Instant.now())
        );

        Optional<Anomaly> anomalies = samplesAnomalyDetector.apply(temperatureReadings);
        assertTrue(anomalies.isPresent());
        assertEquals(anomalies.get().roomId(), roomId);
        assertEquals(anomalies.get().thermometerId(), thermometerId);
        assertEquals(anomalies.get().temperature(), anomaly);
        assertNotNull(anomalies.get().timestamp());
    }

}
