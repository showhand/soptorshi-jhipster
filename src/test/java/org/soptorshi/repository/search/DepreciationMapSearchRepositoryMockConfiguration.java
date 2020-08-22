package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of DepreciationMapSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DepreciationMapSearchRepositoryMockConfiguration {

    @MockBean
    private DepreciationMapSearchRepository mockDepreciationMapSearchRepository;

}
