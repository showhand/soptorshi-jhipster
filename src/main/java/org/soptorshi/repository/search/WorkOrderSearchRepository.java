package org.soptorshi.repository.search;

import org.soptorshi.domain.WorkOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the WorkOrder entity.
 */
public interface WorkOrderSearchRepository extends ElasticsearchRepository<WorkOrder, Long> {
}
