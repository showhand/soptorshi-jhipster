package org.soptorshi.repository.search;

import org.soptorshi.domain.ExperienceInformationAttachment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ExperienceInformationAttachment entity.
 */
public interface ExperienceInformationAttachmentSearchRepository extends ElasticsearchRepository<ExperienceInformationAttachment, Long> {
}
