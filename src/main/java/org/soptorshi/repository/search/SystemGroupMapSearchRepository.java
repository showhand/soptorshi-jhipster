package org.soptorshi.repository.search;

import org.soptorshi.domain.SystemGroupMap;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SystemGroupMap entity.
 */
public interface SystemGroupMapSearchRepository extends ElasticsearchRepository<SystemGroupMap, Long> {
}
