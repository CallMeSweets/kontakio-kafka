package io.kontakt.apps.storage.kafka;

import io.kontak.apps.event.Anomaly;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface ListenerBinding {

    @Input("anomalyDetectorProcessor-in-0")
    KStream<String, Anomaly> inputChannel();

}
