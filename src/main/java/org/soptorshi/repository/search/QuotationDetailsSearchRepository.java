package org.soptorshi.repository.search;

import org.soptorshi.domain.QuotationDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the QuotationDetails entity.
 */
public interface QuotationDetailsSearchRepository extends ElasticsearchRepository<QuotationDetails, Long> {
}
