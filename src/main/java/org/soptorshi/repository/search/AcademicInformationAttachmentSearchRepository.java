package org.soptorshi.repository.search;

import org.soptorshi.domain.AcademicInformationAttachment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AcademicInformationAttachment entity.
 */
public interface AcademicInformationAttachmentSearchRepository extends ElasticsearchRepository<AcademicInformationAttachment, Long> {
}
