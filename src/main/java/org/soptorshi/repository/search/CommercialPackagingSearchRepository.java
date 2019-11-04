package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialPackaging;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialPackaging entity.
 */
public interface CommercialPackagingSearchRepository extends ElasticsearchRepository<CommercialPackaging, Long> {
}
