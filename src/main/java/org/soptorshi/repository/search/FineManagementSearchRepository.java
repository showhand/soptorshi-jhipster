package org.soptorshi.repository.search;

import org.soptorshi.domain.FineManagement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FineManagement entity.
 */
public interface FineManagementSearchRepository extends ElasticsearchRepository<FineManagement, Long> {
}
