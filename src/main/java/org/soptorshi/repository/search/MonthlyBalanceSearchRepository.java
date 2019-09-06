package org.soptorshi.repository.search;

import org.soptorshi.domain.MonthlyBalance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MonthlyBalance entity.
 */
public interface MonthlyBalanceSearchRepository extends ElasticsearchRepository<MonthlyBalance, Long> {
}
