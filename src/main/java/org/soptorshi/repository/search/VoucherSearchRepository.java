package org.soptorshi.repository.search;

import org.soptorshi.domain.Voucher;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Voucher entity.
 */
public interface VoucherSearchRepository extends ElasticsearchRepository<Voucher, Long> {
}
