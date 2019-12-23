package org.soptorshi.repository.search;

import org.soptorshi.domain.LoanManagement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LoanManagement entity.
 */
public interface LoanManagementSearchRepository extends ElasticsearchRepository<LoanManagement, Long> {
}
