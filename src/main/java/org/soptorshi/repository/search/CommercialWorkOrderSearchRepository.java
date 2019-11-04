package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialWorkOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialWorkOrder entity.
 */
public interface CommercialWorkOrderSearchRepository extends ElasticsearchRepository<CommercialWorkOrder, Long> {
}
