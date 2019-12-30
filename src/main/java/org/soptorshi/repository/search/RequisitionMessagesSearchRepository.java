package org.soptorshi.repository.search;

import org.soptorshi.domain.RequisitionMessages;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RequisitionMessages entity.
 */
public interface RequisitionMessagesSearchRepository extends ElasticsearchRepository<RequisitionMessages, Long> {
}
