package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialPurchaseOrderItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialPurchaseOrderItem entity.
 */
public interface CommercialPurchaseOrderItemSearchRepository extends ElasticsearchRepository<CommercialPurchaseOrderItem, Long> {
}
