package org.soptorshi.repository.search;

import org.soptorshi.domain.Office;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Office entity.
 */
public interface OfficeSearchRepository extends ElasticsearchRepository<Office, Long> {
}
