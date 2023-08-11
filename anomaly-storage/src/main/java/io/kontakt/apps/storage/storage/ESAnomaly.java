package io.kontakt.apps.storage.storage;

import java.time.Instant;

public record ESAnomaly(double temperature, String roomId, String thermometerId, long timestamp) {
}
