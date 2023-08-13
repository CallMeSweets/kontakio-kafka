package io.kontakt.apps.storage.storage.source;

import io.kontak.apps.event.Anomaly;

public interface DataStorage {

    void store(Anomaly anomaly);

}
