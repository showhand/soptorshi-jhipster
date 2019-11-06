package org.soptorshi.repository.search;

import org.soptorshi.domain.SupplySalesRepresentative;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SupplySalesRepresentative entity.
 */
public interface SupplySalesRepresentativeSearchRepository extends ElasticsearchRepository<SupplySalesRepresentative, Long> {
}
