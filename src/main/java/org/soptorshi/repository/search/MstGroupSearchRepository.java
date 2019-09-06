package org.soptorshi.repository.search;

import org.soptorshi.domain.MstGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MstGroup entity.
 */
public interface MstGroupSearchRepository extends ElasticsearchRepository<MstGroup, Long> {
}
