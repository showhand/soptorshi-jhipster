package org.soptorshi.repository.search;

import org.soptorshi.domain.RequisitionDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RequisitionDetails entity.
 */
public interface RequisitionDetailsSearchRepository extends ElasticsearchRepository<RequisitionDetails, Long> {
}
