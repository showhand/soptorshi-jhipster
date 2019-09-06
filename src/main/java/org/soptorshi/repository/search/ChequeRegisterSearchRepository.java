package org.soptorshi.repository.search;

import org.soptorshi.domain.ChequeRegister;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ChequeRegister entity.
 */
public interface ChequeRegisterSearchRepository extends ElasticsearchRepository<ChequeRegister, Long> {
}
