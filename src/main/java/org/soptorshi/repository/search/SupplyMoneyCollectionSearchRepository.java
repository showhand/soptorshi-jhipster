package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplyMoneyCollection;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplyMoneyCollection entity.
 */
public interface SupplyMoneyCollectionSearchRepository extends ElasticsearchRepository<SupplyMoneyCollection, Long> {
}
