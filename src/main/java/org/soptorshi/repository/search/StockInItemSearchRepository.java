package org.soptorshi.repository.search;

import org.soptorshi.domain.StockInItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StockInItem entity.
 */
public interface StockInItemSearchRepository extends ElasticsearchRepository<StockInItem, Long> {
}
