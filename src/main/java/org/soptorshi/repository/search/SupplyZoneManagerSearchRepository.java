package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyZoneManager;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyZoneManager entity.
 */
public interface SupplyZoneManagerSearchRepository extends ElasticsearchRepository<SupplyZoneManager, Long> {
}
