package org.soptorshi.service.extended;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.QuotationDetails;
import org.soptorshi.repository.QuotationDetailsRepository;
import org.soptorshi.repository.QuotationRepository;
import org.soptorshi.repository.extended.QuotationDetailsExtendedRepository;
import org.soptorshi.repository.search.QuotationDetailsSearchRepository;
import org.soptorshi.service.QuotationDetailsService;
import org.soptorshi.service.dto.QuotationDetailsDTO;
import org.soptorshi.service.mapper.QuotationDetailsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class QuotationDetailsExtendedService extends QuotationDetailsService {
    private final Logger log = LoggerFactory.getLogger(QuotationDetailsExtendedService.class);

    private QuotationDetailsExtendedRepository quotationDetailsExtendedRepository;
    private QuotationRepository quotationRepository;

    public QuotationDetailsExtendedService(QuotationDetailsRepository quotationDetailsRepository,
                                           QuotationDetailsMapper quotationDetailsMapper,
                                           QuotationDetailsSearchRepository quotationDetailsSearchRepository,
                                           QuotationDetailsExtendedRepository quotationDetailsExtendedRepository,
                                           QuotationRepository quotationRepository) {
        super(quotationDetailsRepository, quotationDetailsMapper, quotationDetailsSearchRepository);
        this.quotationDetailsExtendedRepository = quotationDetailsExtendedRepository;
        this.quotationRepository = quotationRepository;
    }

    public QuotationDetailsDTO save(QuotationDetailsDTO quotationDetailsDTO) {
        log.debug("Request to save QuotationDetails : {}", quotationDetailsDTO);
        QuotationDetails quotationDetails = quotationDetailsMapper.toEntity(quotationDetailsDTO);
        quotationDetails = quotationDetailsRepository.save(quotationDetails);
        QuotationDetailsDTO result = quotationDetailsMapper.toDto(quotationDetails);
        quotationDetailsSearchRepository.save(quotationDetails);
        updateQuotation(quotationDetailsDTO);
        return result;
    }

    private void updateQuotation(QuotationDetailsDTO quotationDetails){
        List<QuotationDetails> quotationDetailsList = quotationDetailsExtendedRepository.findByQuotationId(quotationDetails.getQuotationId());
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(QuotationDetails q: quotationDetailsList){
            totalAmount = totalAmount.add(q.getRate().multiply(new BigDecimal(q.getQuantity())));
        }
        Quotation quotation = quotationRepository.findById(quotationDetails.getQuotationId()).get();
        quotation.setTotalAmount(totalAmount);
        quotationRepository.save(quotation);
    }
}
