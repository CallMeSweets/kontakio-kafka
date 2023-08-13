package io.kontakt.apps.storage.storage.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "temperature-anomalies", writeTypeHint = WriteTypeHint.FALSE)
@Routing("routing")
public class ESAnomaly {
    @Id
    private String id;

    private String routing;
    private double temperature;
    private String roomId;
    private String thermometerId;
    private long timestamp; //todo routing from field in elastic, add file for elastic with mapping, _class from source?

    public ESAnomaly(double temperature, String roomId, String thermometerId, long timestamp) {
        this.routing = roomId + "-" + thermometerId;
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

    public String getId() {
        return id;
    }
}
