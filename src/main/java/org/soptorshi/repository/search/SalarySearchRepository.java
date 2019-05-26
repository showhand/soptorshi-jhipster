package org.soptorshi.repository.search;

import org.soptorshi.domain.Salary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Salary entity.
 */
public interface SalarySearchRepository extends ElasticsearchRepository<Salary, Long> {
}
