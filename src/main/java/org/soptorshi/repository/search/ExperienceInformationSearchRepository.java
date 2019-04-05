package org.soptorshi.repository.search;

import org.soptorshi.domain.ExperienceInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ExperienceInformation entity.
 */
public interface ExperienceInformationSearchRepository extends ElasticsearchRepository<ExperienceInformation, Long> {
}
