package io.kontakt.apps.anomaly.analytics.storage;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "temperature-anomalies")
public class ESAnomaly {
    @Id
    private String id;
    private double temperature;
    private String roomId;
    private String thermometerId;
    private long timestamp;

    public ESAnomaly(double temperature, String roomId, String thermometerId, long timestamp) {
        this.temperature = temperature;
        this.roomId = roomId;
        this.thermometerId = thermometerId;
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getThermometerId() {
        return thermometerId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
