package org.soptorshi.repository.search;

import org.soptorshi.domain.AttendanceExcelUpload;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AttendanceExcelUpload entity.
 */
public interface AttendanceExcelUploadSearchRepository extends ElasticsearchRepository<AttendanceExcelUpload, Long> {
}
