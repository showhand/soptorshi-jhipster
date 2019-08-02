package org.soptorshi.repository.search;

import org.soptorshi.domain.InventorySubLocation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the InventorySubLocation entity.
 */
public interface InventorySubLocationSearchRepository extends ElasticsearchRepository<InventorySubLocation, Long> {
}
