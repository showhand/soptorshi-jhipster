package org.soptorshi.repository.search;

import org.soptorshi.domain.TermsAndConditions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TermsAndConditions entity.
 */
public interface TermsAndConditionsSearchRepository extends ElasticsearchRepository<TermsAndConditions, Long> {
}
