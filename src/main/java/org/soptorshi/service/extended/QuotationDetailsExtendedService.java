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
    private QuotationDetailsMapper quotationDetailsMapper;
    private QuotationDetailsSearchRepository quotationDetailsSearchRepository;


    public QuotationDetailsExtendedService(QuotationDetailsRepository quotationDetailsRepository, QuotationDetailsMapper quotationDetailsMapper, QuotationDetailsSearchRepository quotationDetailsSearchRepository, QuotationDetailsExtendedRepository quotationDetailsExtendedRepository, QuotationRepository quotationRepository, QuotationDetailsMapper quotationDetailsMapper1, QuotationDetailsSearchRepository quotationDetailsSearchRepository1) {
        super(quotationDetailsRepository, quotationDetailsMapper, quotationDetailsSearchRepository);
        this.quotationDetailsExtendedRepository = quotationDetailsExtendedRepository;
        this.quotationRepository = quotationRepository;
        this.quotationDetailsMapper = quotationDetailsMapper1;
        this.quotationDetailsSearchRepository = quotationDetailsSearchRepository1;
    }

    public QuotationDetailsDTO save(QuotationDetailsDTO quotationDetailsDTO) {
        log.debug("Request to save QuotationDetails : {}", quotationDetailsDTO);
        QuotationDetails quotationDetails = quotationDetailsMapper.toEntity(quotationDetailsDTO);
        quotationDetails = quotationDetailsExtendedRepository.save(quotationDetails);
        QuotationDetailsDTO result = quotationDetailsMapper.toDto(quotationDetails);
        quotationDetailsSearchRepository.save(quotationDetails);
        updateQuotation(quotationDetailsDTO);
        return result;
    }

    private void updateQuotation(QuotationDetailsDTO quotationDetails){
        List<QuotationDetails> quotationDetailsList = quotationDetailsExtendedRepository.findByQuotationId(quotationDetails.getQuotationId());
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(QuotationDetails q: quotationDetailsList){
            BigDecimal productTotalAmount = BigDecimal.ZERO;
            productTotalAmount = productTotalAmount.add(q.getRate().multiply(new BigDecimal(q.getQuantity())));
            BigDecimal vat = BigDecimal.ZERO;
            BigDecimal ait = BigDecimal.ZERO;
            if(q.getVatPercentage()!=null)
                vat = productTotalAmount.add(productTotalAmount.multiply(q.getVatPercentage().divide(new BigDecimal(100))));
            if(q.getAitPercentage()!=null)
                ait = productTotalAmount.subtract(productTotalAmount.multiply(q.getAitPercentage().divide(new BigDecimal(100))));
            totalAmount = totalAmount.add(productTotalAmount.add(vat).subtract(ait));

        }
        Quotation quotation = quotationRepository.findById(quotationDetails.getQuotationId()).get();
        quotation.setTotalAmount(totalAmount);
        quotationRepository.save(quotation);
    }
}
