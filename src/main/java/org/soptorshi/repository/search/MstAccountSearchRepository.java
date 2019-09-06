package org.soptorshi.repository.search;

import org.soptorshi.domain.MstAccount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MstAccount entity.
 */
public interface MstAccountSearchRepository extends ElasticsearchRepository<MstAccount, Long> {
}
