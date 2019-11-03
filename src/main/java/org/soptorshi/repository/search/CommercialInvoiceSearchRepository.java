package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialInvoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialInvoice entity.
 */
public interface CommercialInvoiceSearchRepository extends ElasticsearchRepository<CommercialInvoice, Long> {
}
