package org.soptorshi.repository.search;

import org.soptorshi.domain.DepartmentHead;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DepartmentHead entity.
 */
public interface DepartmentHeadSearchRepository extends ElasticsearchRepository<DepartmentHead, Long> {
}
