package io.kontakt.apps.storage.storage;

import io.kontak.apps.event.Anomaly;

public interface DataStorage {

    void store(Anomaly anomaly);

}
