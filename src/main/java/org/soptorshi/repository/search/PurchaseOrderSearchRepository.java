package org.soptorshi.repository.search;

import org.soptorshi.domain.PurchaseOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PurchaseOrder entity.
 */
public interface PurchaseOrderSearchRepository extends ElasticsearchRepository<PurchaseOrder, Long> {
}
