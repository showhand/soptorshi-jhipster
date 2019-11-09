package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyOrderDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyOrderDetails entity.
 */
public interface SupplyOrderDetailsSearchRepository extends ElasticsearchRepository<SupplyOrderDetails, Long> {
}
