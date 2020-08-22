package org.soptorshi.repository.search;

import org.soptorshi.domain.DepreciationMap;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DepreciationMap entity.
 */
public interface DepreciationMapSearchRepository extends ElasticsearchRepository<DepreciationMap, Long> {
}
