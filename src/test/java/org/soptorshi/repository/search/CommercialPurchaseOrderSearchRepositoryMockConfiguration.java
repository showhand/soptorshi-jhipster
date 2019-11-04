package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CommercialPurchaseOrderSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CommercialPurchaseOrderSearchRepositoryMockConfiguration {

    @MockBean
    private CommercialPurchaseOrderSearchRepository mockCommercialPurchaseOrderSearchRepository;

}
