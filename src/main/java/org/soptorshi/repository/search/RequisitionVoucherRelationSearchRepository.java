package org.soptorshi.repository.search;

import org.soptorshi.domain.RequisitionVoucherRelation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RequisitionVoucherRelation entity.
 */
public interface RequisitionVoucherRelationSearchRepository extends ElasticsearchRepository<RequisitionVoucherRelation, Long> {
}
