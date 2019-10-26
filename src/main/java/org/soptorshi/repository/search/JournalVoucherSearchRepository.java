package org.soptorshi.repository.search;

import org.soptorshi.domain.JournalVoucher;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JournalVoucher entity.
 */
public interface JournalVoucherSearchRepository extends ElasticsearchRepository<JournalVoucher, Long> {
}
