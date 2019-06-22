package org.soptorshi.repository.search;

import org.soptorshi.domain.PurchaseCommittee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PurchaseCommittee entity.
 */
public interface PurchaseCommitteeSearchRepository extends ElasticsearchRepository<PurchaseCommittee, Long> {
}
