package org.soptorshi.repository.search;

import org.soptorshi.domain.AdvanceManagement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdvanceManagement entity.
 */
public interface AdvanceManagementSearchRepository extends ElasticsearchRepository<AdvanceManagement, Long> {
}
