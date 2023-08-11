package io.kontakt.apps.anomaly.analytics.api.controllers;

import io.kontakt.apps.anomaly.analytics.storage.ESAnomaly;
import io.kontakt.apps.anomaly.analytics.storage.service.AnomalyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AnomaliesController {

    @Resource
    private AnomalyService anomalyService;

    @GetMapping(value = "/room/{roomId}/anomalies", produces = "application/json")
    public List<ESAnomaly> roomAnomalies(@PathVariable String roomId) {
        return anomalyService.findAnomaliesByRoomId(roomId);
    }

    @GetMapping(value = "/thermometer/{thermometerId}/anomalies", produces = "application/json")
    public List<ESAnomaly> thermometerAnomalies(@PathVariable String thermometerId) {
        return anomalyService.findAnomaliesByThermometerId(thermometerId);
    }

    @GetMapping(value = "/thermometers", produces = "application/json")
    public Set<String> thermometersByThreshold(@RequestParam double threshold) {
        return anomalyService.findAnomaliesAboveThreshold(threshold)
                .stream()
                .map(ESAnomaly::getThermometerId)
                .collect(Collectors.toSet());
    }

}
