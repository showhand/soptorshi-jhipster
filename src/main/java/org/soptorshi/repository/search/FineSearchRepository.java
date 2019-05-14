package org.soptorshi.repository.search;

import org.soptorshi.domain.Fine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Fine entity.
 */
public interface FineSearchRepository extends ElasticsearchRepository<Fine, Long> {
}
