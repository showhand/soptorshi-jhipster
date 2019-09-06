package org.soptorshi.repository.search;

import org.soptorshi.domain.PeriodClose;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PeriodClose entity.
 */
public interface PeriodCloseSearchRepository extends ElasticsearchRepository<PeriodClose, Long> {
}
