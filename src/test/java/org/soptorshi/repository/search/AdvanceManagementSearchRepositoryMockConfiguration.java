package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of AdvanceManagementSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AdvanceManagementSearchRepositoryMockConfiguration {

    @MockBean
    private AdvanceManagementSearchRepository mockAdvanceManagementSearchRepository;

}
