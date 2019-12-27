package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialProductInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialProductInfo entity.
 */
public interface CommercialProductInfoSearchRepository extends ElasticsearchRepository<CommercialProductInfo, Long> {
}
