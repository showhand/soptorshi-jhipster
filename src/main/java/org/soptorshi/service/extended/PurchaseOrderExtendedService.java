package org.soptorshi.service.extended;

import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.enumeration.SelectionType;
import org.soptorshi.repository.PurchaseOrderRepository;
import org.soptorshi.repository.extended.QuotationExtendedRepository;
import org.soptorshi.repository.extended.RequisitionExtendedRepository;
import org.soptorshi.repository.search.PurchaseOrderSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.PurchaseOrderService;
import org.soptorshi.service.dto.PurchaseOrderDTO;
import org.soptorshi.service.mapper.PurchaseOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class PurchaseOrderExtendedService extends PurchaseOrderService {

    private final QuotationExtendedRepository quotationExtendedRepository;

    private final RequisitionExtendedRepository requisitionExtendedRepository;

    public PurchaseOrderExtendedService(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderMapper purchaseOrderMapper, PurchaseOrderSearchRepository purchaseOrderSearchRepository, QuotationExtendedRepository quotationExtendedRepository, RequisitionExtendedRepository requisitionExtendedRepository) {
        super(purchaseOrderRepository, purchaseOrderMapper, purchaseOrderSearchRepository);
        this.quotationExtendedRepository = quotationExtendedRepository;
        this.requisitionExtendedRepository = requisitionExtendedRepository;
    }

    @Override
    public PurchaseOrderDTO save(PurchaseOrderDTO purchaseOrderDTO) {
        purchaseOrderDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        purchaseOrderDTO.setModifiedOn(LocalDate.now());
        Quotation quotation = purchaseOrderDTO.getRequisitionId()==null?null:  quotationExtendedRepository.findByRequisitionAndAndSelectionStatus(requisitionExtendedRepository.getOne(purchaseOrderDTO.getRequisitionId()), SelectionType.SELECTED).get();
        if(quotation!=null){
            purchaseOrderDTO.setQuotationId(quotation.getId());
        }
        return super.save(purchaseOrderDTO);
    }
}
