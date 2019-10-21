package org.soptorshi.repository.search;

import org.soptorshi.domain.ReceiptVoucherGenerator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ReceiptVoucherGenerator entity.
 */
public interface ReceiptVoucherGeneratorSearchRepository extends ElasticsearchRepository<ReceiptVoucherGenerator, Long> {
}
