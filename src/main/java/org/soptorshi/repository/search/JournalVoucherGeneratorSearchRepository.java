package org.soptorshi.repository.search;

import org.soptorshi.domain.JournalVoucherGenerator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JournalVoucherGenerator entity.
 */
public interface JournalVoucherGeneratorSearchRepository extends ElasticsearchRepository<JournalVoucherGenerator, Long> {
}
