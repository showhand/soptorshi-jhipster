package org.soptorshi.repository.search;

import org.soptorshi.domain.ProvidentManagement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProvidentManagement entity.
 */
public interface ProvidentManagementSearchRepository extends ElasticsearchRepository<ProvidentManagement, Long> {
}
