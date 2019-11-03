package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialPackagingDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialPackagingDetails entity.
 */
public interface CommercialPackagingDetailsSearchRepository extends ElasticsearchRepository<CommercialPackagingDetails, Long> {
}
