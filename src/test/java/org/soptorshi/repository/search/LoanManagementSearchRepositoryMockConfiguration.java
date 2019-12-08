package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of LoanManagementSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LoanManagementSearchRepositoryMockConfiguration {

    @MockBean
    private LoanManagementSearchRepository mockLoanManagementSearchRepository;

}
