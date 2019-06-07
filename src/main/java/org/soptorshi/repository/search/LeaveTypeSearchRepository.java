package org.soptorshi.repository.search;

import org.soptorshi.domain.LeaveType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LeaveType entity.
 */
public interface LeaveTypeSearchRepository extends ElasticsearchRepository<LeaveType, Long> {
}
