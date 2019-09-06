package org.soptorshi.repository.search;

import org.soptorshi.domain.CreditorLedger;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CreditorLedger entity.
 */
public interface CreditorLedgerSearchRepository extends ElasticsearchRepository<CreditorLedger, Long> {
}
