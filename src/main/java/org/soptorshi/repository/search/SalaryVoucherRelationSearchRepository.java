package org.soptorshi.repository.search;

import org.soptorshi.domain.SalaryVoucherRelation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SalaryVoucherRelation entity.
 */
public interface SalaryVoucherRelationSearchRepository extends ElasticsearchRepository<SalaryVoucherRelation, Long> {
}
