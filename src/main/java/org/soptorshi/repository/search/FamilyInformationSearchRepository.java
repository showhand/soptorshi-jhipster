package org.soptorshi.repository.search;

import org.soptorshi.domain.FamilyInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FamilyInformation entity.
 */
public interface FamilyInformationSearchRepository extends ElasticsearchRepository<FamilyInformation, Long> {
}
