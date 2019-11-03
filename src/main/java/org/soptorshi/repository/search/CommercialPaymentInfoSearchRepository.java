package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialPaymentInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialPaymentInfo entity.
 */
public interface CommercialPaymentInfoSearchRepository extends ElasticsearchRepository<CommercialPaymentInfo, Long> {
}
