package org.soptorshi.repository.search;

import org.soptorshi.domain.OverTime;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OverTime entity.
 */
public interface OverTimeSearchRepository extends ElasticsearchRepository<OverTime, Long> {
}
