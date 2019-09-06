package org.soptorshi.repository.search;

import org.soptorshi.domain.VoucherNumberControl;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VoucherNumberControl entity.
 */
public interface VoucherNumberControlSearchRepository extends ElasticsearchRepository<VoucherNumberControl, Long> {
}
