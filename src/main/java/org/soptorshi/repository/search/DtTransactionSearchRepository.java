package org.soptorshi.repository.search;

import org.soptorshi.domain.DtTransaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DtTransaction entity.
 */
public interface DtTransactionSearchRepository extends ElasticsearchRepository<DtTransaction, Long> {
}
