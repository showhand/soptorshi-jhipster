package org.soptorshi.repository.search;

import org.soptorshi.domain.Holiday;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Holiday entity.
 */
public interface HolidaySearchRepository extends ElasticsearchRepository<Holiday, Long> {
}
