package org.soptorshi.repository.search;

import org.soptorshi.domain.Production;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Production entity.
 */
public interface ProductionSearchRepository extends ElasticsearchRepository<Production, Long> {
}
