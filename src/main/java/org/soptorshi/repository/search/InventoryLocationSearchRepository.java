package org.soptorshi.repository.search;

import org.soptorshi.domain.InventoryLocation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the InventoryLocation entity.
 */
public interface InventoryLocationSearchRepository extends ElasticsearchRepository<InventoryLocation, Long> {
}
