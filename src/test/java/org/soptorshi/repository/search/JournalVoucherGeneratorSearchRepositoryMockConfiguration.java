package org.soptorshi.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of JournalVoucherGeneratorSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class JournalVoucherGeneratorSearchRepositoryMockConfiguration {

    @MockBean
    private JournalVoucherGeneratorSearchRepository mockJournalVoucherGeneratorSearchRepository;

}
