package org.soptorshi.repository.search;

import org.soptorshi.domain.Weekend;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Weekend entity.
 */
public interface WeekendSearchRepository extends ElasticsearchRepository<Weekend, Long> {
}
