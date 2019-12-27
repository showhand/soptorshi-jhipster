package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialPi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialPi entity.
 */
public interface CommercialPiSearchRepository extends ElasticsearchRepository<CommercialPi, Long> {
}
