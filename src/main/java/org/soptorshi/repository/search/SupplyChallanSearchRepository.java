package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyChallan;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyChallan entity.
 */
public interface SupplyChallanSearchRepository extends ElasticsearchRepository<SupplyChallan, Long> {
}
