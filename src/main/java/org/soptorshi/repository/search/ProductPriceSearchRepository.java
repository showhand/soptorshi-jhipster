package org.soptorshi.repository.search;

import org.soptorshi.domain.ProductPrice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductPrice entity.
 */
public interface ProductPriceSearchRepository extends ElasticsearchRepository<ProductPrice, Long> {
}
