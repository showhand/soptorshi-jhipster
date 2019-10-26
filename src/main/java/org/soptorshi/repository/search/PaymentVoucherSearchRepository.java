package org.soptorshi.repository.search;

import org.soptorshi.domain.PaymentVoucher;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PaymentVoucher entity.
 */
public interface PaymentVoucherSearchRepository extends ElasticsearchRepository<PaymentVoucher, Long> {
}
