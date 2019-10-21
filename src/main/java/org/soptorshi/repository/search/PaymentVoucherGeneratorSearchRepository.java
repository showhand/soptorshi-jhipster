package org.soptorshi.repository.search;

import org.soptorshi.domain.PaymentVoucherGenerator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PaymentVoucherGenerator entity.
 */
public interface PaymentVoucherGeneratorSearchRepository extends ElasticsearchRepository<PaymentVoucherGenerator, Long> {
}
