package org.soptorshi.repository.search;

import org.soptorshi.domain.ConversionFactor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ConversionFactor entity.
 */
public interface ConversionFactorSearchRepository extends ElasticsearchRepository<ConversionFactor, Long> {
}
