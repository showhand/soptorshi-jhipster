package org.soptorshi.repository.search;

import org.soptorshi.domain.StockOutItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StockOutItem entity.
 */
public interface StockOutItemSearchRepository extends ElasticsearchRepository<StockOutItem, Long> {
}
