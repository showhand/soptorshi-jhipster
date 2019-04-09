package org.soptorshi.repository.search;

import org.soptorshi.domain.TrainingInformationAttachment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TrainingInformationAttachment entity.
 */
public interface TrainingInformationAttachmentSearchRepository extends ElasticsearchRepository<TrainingInformationAttachment, Long> {
}
