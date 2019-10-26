package org.soptorshi.repository.search;

import org.soptorshi.domain.ContraVoucher;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ContraVoucher entity.
 */
public interface ContraVoucherSearchRepository extends ElasticsearchRepository<ContraVoucher, Long> {
}
