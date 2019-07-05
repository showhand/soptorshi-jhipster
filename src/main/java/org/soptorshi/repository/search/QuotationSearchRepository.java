package org.soptorshi.repository.search;

import org.soptorshi.domain.Quotation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Quotation entity.
 */
public interface QuotationSearchRepository extends ElasticsearchRepository<Quotation, Long> {
}
