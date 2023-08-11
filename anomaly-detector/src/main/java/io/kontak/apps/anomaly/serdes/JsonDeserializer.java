package io.kontak.apps.anomaly.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonDeserializer<T> implements Deserializer<T> {

    Logger logger = LoggerFactory.getLogger(JsonDeserializer.class);


    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    private final Class<T> destinationClass;

    public JsonDeserializer(Class<T> destinationClass) {
        this.destinationClass = destinationClass;
    }

    @Override
    public T deserialize(String s, byte[] data) {
        if(data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, destinationClass);
        } catch (IOException e) {
            logger.error("IOException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
