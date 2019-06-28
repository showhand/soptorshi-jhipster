package org.soptorshi.repository.search;

import org.soptorshi.domain.LeaveAttachment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LeaveAttachment entity.
 */
public interface LeaveAttachmentSearchRepository extends ElasticsearchRepository<LeaveAttachment, Long> {
}
