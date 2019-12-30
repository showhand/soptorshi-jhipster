package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of RequisitionVoucherRelationSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RequisitionVoucherRelationSearchRepositoryMockConfiguration {

    @MockBean
    private RequisitionVoucherRelationSearchRepository mockRequisitionVoucherRelationSearchRepository;

}
