package org.soptorshi.repository.search;

import org.soptorshi.domain.VendorContactPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VendorContactPerson entity.
 */
public interface VendorContactPersonSearchRepository extends ElasticsearchRepository<VendorContactPerson, Long> {
}
