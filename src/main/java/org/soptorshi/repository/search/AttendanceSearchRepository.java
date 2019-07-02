package org.soptorshi.repository.search;

import org.soptorshi.domain.Attendance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Attendance entity.
 */
public interface AttendanceSearchRepository extends ElasticsearchRepository<Attendance, Long> {
}
