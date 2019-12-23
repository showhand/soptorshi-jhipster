package org.soptorshi.repository.search;

import org.soptorshi.domain.MontlySalaryVouchers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MontlySalaryVouchers entity.
 */
public interface MontlySalaryVouchersSearchRepository extends ElasticsearchRepository<MontlySalaryVouchers, Long> {
}
