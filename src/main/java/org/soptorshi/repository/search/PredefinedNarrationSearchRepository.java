package org.soptorshi.repository.search;

import org.soptorshi.domain.PredefinedNarration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PredefinedNarration entity.
 */
public interface PredefinedNarrationSearchRepository extends ElasticsearchRepository<PredefinedNarration, Long> {
}
