package org.soptorshi.repository.search;

import org.soptorshi.domain.Requisition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Requisition entity.
 */
public interface RequisitionSearchRepository extends ElasticsearchRepository<Requisition, Long> {
}
