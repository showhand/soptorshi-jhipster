package org.soptorshi.repository.search;

import org.soptorshi.domain.DepreciationCalculation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DepreciationCalculation entity.
 */
public interface DepreciationCalculationSearchRepository extends ElasticsearchRepository<DepreciationCalculation, Long> {
}
