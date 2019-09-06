package org.soptorshi.repository.search;

import org.soptorshi.domain.AccountBalance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AccountBalance entity.
 */
public interface AccountBalanceSearchRepository extends ElasticsearchRepository<AccountBalance, Long> {
}
