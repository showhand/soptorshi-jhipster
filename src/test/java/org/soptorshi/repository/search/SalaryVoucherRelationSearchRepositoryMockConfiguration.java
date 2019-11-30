package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of SalaryVoucherRelationSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SalaryVoucherRelationSearchRepositoryMockConfiguration {

    @MockBean
    private SalaryVoucherRelationSearchRepository mockSalaryVoucherRelationSearchRepository;

}
