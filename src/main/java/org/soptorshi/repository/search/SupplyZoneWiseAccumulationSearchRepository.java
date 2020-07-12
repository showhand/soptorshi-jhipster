package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyZoneWiseAccumulation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyZoneWiseAccumulation entity.
 */
public interface SupplyZoneWiseAccumulationSearchRepository extends ElasticsearchRepository<SupplyZoneWiseAccumulation, Long> {
}
