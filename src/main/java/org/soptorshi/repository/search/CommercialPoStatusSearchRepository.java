package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialPoStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialPoStatus entity.
 */
public interface CommercialPoStatusSearchRepository extends ElasticsearchRepository<CommercialPoStatus, Long> {
}
