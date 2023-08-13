package io.kontakt.apps.storage.storage.repository;

import io.kontakt.apps.storage.storage.model.ESAnomaly;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalyRepository extends ElasticsearchRepository<ESAnomaly, String> {
}
