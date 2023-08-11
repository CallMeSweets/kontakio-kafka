package io.kontak.apps.anomaly.detectors;

import io.kontak.apps.anomaly.model.TemperatureToAnomalyMapper;
import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Component
public class TimePeriodAnomalyDetector implements AnomalyDetector {
    @Value("${anomaly.detector.threshold}")
    private double threshold;

    @Resource
    private TemperatureToAnomalyMapper mapper;

    @Override
    public Optional<Anomaly> apply(List<TemperatureReading> temperatureReadings) {
        TemperatureReading temperatureAnomaly = findTemperatureAnomaly(temperatureReadings);
        if(temperatureAnomaly == null) {
            return Optional.empty();
        }

        return Optional.of(mapper.apply(temperatureAnomaly));
    }

    private TemperatureReading findTemperatureAnomaly(List<TemperatureReading> temperatures) {
        for (TemperatureReading temp : temperatures) {
            double sumOfOthers = 0.0;
            for (TemperatureReading otherTemp : temperatures) {
                if (!otherTemp.equals(temp)) {
                    sumOfOthers += otherTemp.temperature();
                }
            }

            double avgOfOthers = sumOfOthers / (temperatures.size());
            if (temp.temperature() - avgOfOthers >= threshold) {
                return temp;
            }
        }

        return null;
    }
}
