package org.soptorshi.repository.search;

import org.soptorshi.domain.FinancialAccountYear;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FinancialAccountYear entity.
 */
public interface FinancialAccountYearSearchRepository extends ElasticsearchRepository<FinancialAccountYear, Long> {
}
