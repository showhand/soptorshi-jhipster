package org.soptorshi.repository.search;

import org.soptorshi.domain.SystemAccountMap;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SystemAccountMap entity.
 */
public interface SystemAccountMapSearchRepository extends ElasticsearchRepository<SystemAccountMap, Long> {
}
