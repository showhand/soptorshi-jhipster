package org.soptorshi.repository.search;

import org.soptorshi.domain.TrainingInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TrainingInformation entity.
 */
public interface TrainingInformationSearchRepository extends ElasticsearchRepository<TrainingInformation, Long> {
}
