package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyOrder entity.
 */
public interface SupplyOrderSearchRepository extends ElasticsearchRepository<SupplyOrder, Long> {
}
