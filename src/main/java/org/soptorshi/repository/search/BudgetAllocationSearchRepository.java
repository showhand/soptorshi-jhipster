package org.soptorshi.repository.search;

import org.soptorshi.domain.BudgetAllocation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BudgetAllocation entity.
 */
public interface BudgetAllocationSearchRepository extends ElasticsearchRepository<BudgetAllocation, Long> {
}
