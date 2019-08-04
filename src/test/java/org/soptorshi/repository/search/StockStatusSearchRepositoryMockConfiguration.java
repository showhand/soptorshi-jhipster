package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of StockStatusSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class StockStatusSearchRepositoryMockConfiguration {

    @MockBean
    private StockStatusSearchRepository mockStockStatusSearchRepository;

}
