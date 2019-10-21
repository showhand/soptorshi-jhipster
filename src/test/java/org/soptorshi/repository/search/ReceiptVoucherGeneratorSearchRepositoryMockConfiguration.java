package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ReceiptVoucherGeneratorSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ReceiptVoucherGeneratorSearchRepositoryMockConfiguration {

    @MockBean
    private ReceiptVoucherGeneratorSearchRepository mockReceiptVoucherGeneratorSearchRepository;

}
