package io.kontakt.apps.anomaly.analytics.storage.model;


import java.time.LocalDateTime;

public record AnomalyDTO(double temperature, String roomId, String thermometerId, LocalDateTime date) {
}
