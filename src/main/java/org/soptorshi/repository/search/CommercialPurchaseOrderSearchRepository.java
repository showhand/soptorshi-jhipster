package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialPurchaseOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialPurchaseOrder entity.
 */
public interface CommercialPurchaseOrderSearchRepository extends ElasticsearchRepository<CommercialPurchaseOrder, Long> {
}
