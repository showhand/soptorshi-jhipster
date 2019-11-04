package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialWorkOrderDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialWorkOrderDetails entity.
 */
public interface CommercialWorkOrderDetailsSearchRepository extends ElasticsearchRepository<CommercialWorkOrderDetails, Long> {
}
