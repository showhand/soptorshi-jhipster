package org.soptorshi.repository.search;

import org.soptorshi.domain.ProvidentFund;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProvidentFund entity.
 */
public interface ProvidentFundSearchRepository extends ElasticsearchRepository<ProvidentFund, Long> {
}
