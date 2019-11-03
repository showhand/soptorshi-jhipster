package org.soptorshi.repository.search;

import org.soptorshi.domain.CommercialProformaInvoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CommercialProformaInvoice entity.
 */
public interface CommercialProformaInvoiceSearchRepository extends ElasticsearchRepository<CommercialProformaInvoice, Long> {
}
