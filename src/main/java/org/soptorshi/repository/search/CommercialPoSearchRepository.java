package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialPo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialPo entity.
 */
public interface CommercialPoSearchRepository extends ElasticsearchRepository<CommercialPo, Long> {
}
