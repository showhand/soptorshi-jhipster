package org.soptorshi.repository.search;

import org.soptorshi.domain.StockInProcess;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StockInProcess entity.
 */
public interface StockInProcessSearchRepository extends ElasticsearchRepository<StockInProcess, Long> {
}
