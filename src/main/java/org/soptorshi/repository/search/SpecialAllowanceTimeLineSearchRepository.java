package org.soptorshi.repository.search;

import org.soptorshi.domain.SpecialAllowanceTimeLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SpecialAllowanceTimeLine entity.
 */
public interface SpecialAllowanceTimeLineSearchRepository extends ElasticsearchRepository<SpecialAllowanceTimeLine, Long> {
}
