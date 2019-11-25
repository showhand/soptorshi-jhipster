package org.soptorshi.repository.search;

import org.soptorshi.domain.SalaryMessages;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SalaryMessages entity.
 */
public interface SalaryMessagesSearchRepository extends ElasticsearchRepository<SalaryMessages, Long> {
}
