package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of SpecialAllowanceTimeLineSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SpecialAllowanceTimeLineSearchRepositoryMockConfiguration {

    @MockBean
    private SpecialAllowanceTimeLineSearchRepository mockSpecialAllowanceTimeLineSearchRepository;

}
