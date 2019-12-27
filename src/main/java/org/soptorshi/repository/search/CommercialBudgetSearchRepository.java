package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialBudget;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialBudget entity.
 */
public interface CommercialBudgetSearchRepository extends ElasticsearchRepository<CommercialBudget, Long> {
}
