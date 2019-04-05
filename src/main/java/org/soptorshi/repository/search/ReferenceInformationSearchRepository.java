package org.soptorshi.repository.search;

import org.soptorshi.domain.ReferenceInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ReferenceInformation entity.
 */
public interface ReferenceInformationSearchRepository extends ElasticsearchRepository<ReferenceInformation, Long> {
}
