package org.soptorshi.repository.extended;

import org.soptorshi.domain.QuotationDetails;
import org.soptorshi.repository.QuotationDetailsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationDetailsExtendedRepository extends QuotationDetailsRepository {
    List<QuotationDetails> findByQuotationId(Long quotationId);
}
