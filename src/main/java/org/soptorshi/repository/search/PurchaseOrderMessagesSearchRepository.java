package org.soptorshi.repository.search;

import org.soptorshi.domain.PurchaseOrderMessages;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PurchaseOrderMessages entity.
 */
public interface PurchaseOrderMessagesSearchRepository extends ElasticsearchRepository<PurchaseOrderMessages, Long> {
}
