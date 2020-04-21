package org.soptorshi.repository.search;

import org.soptorshi.domain.PurchaseOrderVoucherRelation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PurchaseOrderVoucherRelation entity.
 */
public interface PurchaseOrderVoucherRelationSearchRepository extends ElasticsearchRepository<PurchaseOrderVoucherRelation, Long> {
}
