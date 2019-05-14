package org.soptorshi.repository.search;

import org.soptorshi.domain.Advance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Advance entity.
 */
public interface AdvanceSearchRepository extends ElasticsearchRepository<Advance, Long> {
}
