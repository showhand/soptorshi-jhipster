package org.soptorshi.repository.search;

import org.soptorshi.domain.DebtorLedger;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DebtorLedger entity.
 */
public interface DebtorLedgerSearchRepository extends ElasticsearchRepository<DebtorLedger, Long> {
}
