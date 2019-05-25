package org.soptorshi.repository.search;

import org.soptorshi.domain.HolidayType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the HolidayType entity.
 */
public interface HolidayTypeSearchRepository extends ElasticsearchRepository<HolidayType, Long> {
}
