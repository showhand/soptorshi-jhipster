package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.enumeration.SelectionType;
import org.soptorshi.repository.QuotationRepository;
import org.soptorshi.repository.RequisitionRepository;
import org.soptorshi.repository.extended.QuotationExtendedRepository;
import org.soptorshi.repository.search.QuotationSearchRepository;
import org.soptorshi.service.QuotationService;
import org.soptorshi.service.dto.QuotationDTO;
import org.soptorshi.service.mapper.QuotationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuotationExtendedService extends QuotationService {
    private final Logger log = LoggerFactory.getLogger(QuotationExtendedService.class);


    private QuotationExtendedRepository quotationExtendedRepository;
    private RequisitionRepository requisitionRepository;

    public QuotationExtendedService(QuotationRepository quotationRepository,
                                    QuotationMapper quotationMapper,
                                    QuotationSearchRepository quotationSearchRepository,
                                    QuotationExtendedRepository quotationExtendedRepository,
                                    RequisitionRepository requisitionRepository) {
        super(quotationRepository, quotationMapper, quotationSearchRepository);
        this.quotationExtendedRepository = quotationExtendedRepository;
        this.requisitionRepository = requisitionRepository;
    }

    @Override
    public QuotationDTO save(QuotationDTO quotationDTO) {
        if(quotationDTO.getSelectionStatus()!=null && quotationDTO.getSelectionStatus().equals(SelectionType.SELECTED)){
            Requisition requisition = requisitionRepository.findById(quotationDTO.getRequisitionId()).get();
            requisition.setAmount(quotationDTO.getTotalAmount());
        }
        return super.save(quotationDTO);

    }
}
