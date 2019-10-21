package org.soptorshi.repository.search;

import org.soptorshi.domain.ContraVoucherGenerator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ContraVoucherGenerator entity.
 */
public interface ContraVoucherGeneratorSearchRepository extends ElasticsearchRepository<ContraVoucherGenerator, Long> {
}
