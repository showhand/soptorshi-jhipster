package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyZone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyZone entity.
 */
public interface SupplyZoneSearchRepository extends ElasticsearchRepository<SupplyZone, Long> {
}
