package org.soptorshi.repository.search;

import org.soptorshi.domain.ReceiptVoucher;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ReceiptVoucher entity.
 */
public interface ReceiptVoucherSearchRepository extends ElasticsearchRepository<ReceiptVoucher, Long> {
}
