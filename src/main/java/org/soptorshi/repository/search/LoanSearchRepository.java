package org.soptorshi.repository.search;

import org.soptorshi.domain.Loan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Loan entity.
 */
public interface LoanSearchRepository extends ElasticsearchRepository<Loan, Long> {
}
