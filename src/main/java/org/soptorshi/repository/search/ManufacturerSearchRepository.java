package org.soptorshi.repository.search;

import org.soptorshi.domain.Manufacturer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Manufacturer entity.
 */
public interface ManufacturerSearchRepository extends ElasticsearchRepository<Manufacturer, Long> {
}
