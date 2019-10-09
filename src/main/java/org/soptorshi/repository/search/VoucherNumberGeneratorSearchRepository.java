package org.soptorshi.repository.search;

import org.soptorshi.domain.VoucherNumberGenerator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VoucherNumberGenerator entity.
 */
public interface VoucherNumberGeneratorSearchRepository extends ElasticsearchRepository<VoucherNumberGenerator, Long> {
}
