package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialAttachment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialAttachment entity.
 */
public interface CommercialAttachmentSearchRepository extends ElasticsearchRepository<CommercialAttachment, Long> {
}
