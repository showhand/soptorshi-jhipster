package org.soptorshi.repository.search;

import org.soptorshi.domain.LeaveApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LeaveApplication entity.
 */
public interface LeaveApplicationSearchRepository extends ElasticsearchRepository<LeaveApplication, Long> {
}
