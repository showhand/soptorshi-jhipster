package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CommercialBudgetSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CommercialBudgetSearchRepositoryMockConfiguration {

    @MockBean
    private CommercialBudgetSearchRepository mockCommercialBudgetSearchRepository;

}
