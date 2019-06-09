package org.soptorshi.repository.search;

import org.soptorshi.domain.DesignationWiseAllowance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignationWiseAllowance entity.
 */
public interface DesignationWiseAllowanceSearchRepository extends ElasticsearchRepository<DesignationWiseAllowance, Long> {
}
