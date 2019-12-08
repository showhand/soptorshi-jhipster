package org.soptorshi.repository.search;

import org.soptorshi.domain.FineAdvanceLoanManagement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FineAdvanceLoanManagement entity.
 */
public interface FineAdvanceLoanManagementSearchRepository extends ElasticsearchRepository<FineAdvanceLoanManagement, Long> {
}
