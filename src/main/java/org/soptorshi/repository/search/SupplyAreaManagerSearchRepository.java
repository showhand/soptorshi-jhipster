package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyAreaManager;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyAreaManager entity.
 */
public interface SupplyAreaManagerSearchRepository extends ElasticsearchRepository<SupplyAreaManager, Long> {
}
