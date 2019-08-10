package org.soptorshi.repository.search;

import org.soptorshi.domain.StockStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StockStatus entity.
 */
public interface StockStatusSearchRepository extends ElasticsearchRepository<StockStatus, Long> {
}
