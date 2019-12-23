package org.soptorshi.repository.search;

import org.soptorshi.domain.AllowanceManagement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AllowanceManagement entity.
 */
public interface AllowanceManagementSearchRepository extends ElasticsearchRepository<AllowanceManagement, Long> {
}
