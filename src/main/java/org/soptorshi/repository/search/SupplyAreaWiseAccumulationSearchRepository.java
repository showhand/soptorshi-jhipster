package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyAreaWiseAccumulation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyAreaWiseAccumulation entity.
 */
public interface SupplyAreaWiseAccumulationSearchRepository extends ElasticsearchRepository<SupplyAreaWiseAccumulation, Long> {
}
