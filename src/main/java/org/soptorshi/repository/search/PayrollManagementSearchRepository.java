package org.soptorshi.repository.search;

import org.soptorshi.domain.PayrollManagement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PayrollManagement entity.
 */
public interface PayrollManagementSearchRepository extends ElasticsearchRepository<PayrollManagement, Long> {
}
