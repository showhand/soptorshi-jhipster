package org.soptorshi.repository.search;

import org.soptorshi.domain.Vendor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Vendor entity.
 */
public interface VendorSearchRepository extends ElasticsearchRepository<Vendor, Long> {
}
