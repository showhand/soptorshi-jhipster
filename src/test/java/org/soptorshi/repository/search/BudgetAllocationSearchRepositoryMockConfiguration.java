package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of BudgetAllocationSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class BudgetAllocationSearchRepositoryMockConfiguration {

    @MockBean
    private BudgetAllocationSearchRepository mockBudgetAllocationSearchRepository;

}
