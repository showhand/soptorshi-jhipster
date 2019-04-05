package org.soptorshi.repository.search;

import org.soptorshi.domain.AcademicInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AcademicInformation entity.
 */
public interface AcademicInformationSearchRepository extends ElasticsearchRepository<AcademicInformation, Long> {
}
