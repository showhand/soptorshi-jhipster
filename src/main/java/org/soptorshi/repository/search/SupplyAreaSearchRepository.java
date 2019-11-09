package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyArea;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyArea entity.
 */
public interface SupplyAreaSearchRepository extends ElasticsearchRepository<SupplyArea, Long> {
}
