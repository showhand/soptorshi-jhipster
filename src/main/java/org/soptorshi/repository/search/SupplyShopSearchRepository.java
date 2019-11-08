package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyShop;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyShop entity.
 */
public interface SupplyShopSearchRepository extends ElasticsearchRepository<SupplyShop, Long> {
}
