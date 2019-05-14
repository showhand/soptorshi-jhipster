package org.soptorshi.repository.search;

import org.soptorshi.domain.MonthlySalary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MonthlySalary entity.
 */
public interface MonthlySalarySearchRepository extends ElasticsearchRepository<MonthlySalary, Long> {
}
