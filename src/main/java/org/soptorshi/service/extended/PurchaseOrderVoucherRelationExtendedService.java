package org.soptorshi.service.extended;

import org.soptorshi.domain.enumeration.ApplicationType;
import org.soptorshi.repository.PurchaseOrderVoucherRelationRepository;
import org.soptorshi.repository.extended.PurchaseOrderVoucherRelationExtendedRepository;
import org.soptorshi.repository.search.PurchaseOrderVoucherRelationSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.PurchaseOrderVoucherRelationService;
import org.soptorshi.service.dto.PurchaseOrderVoucherRelationDTO;
import org.soptorshi.service.mapper.PurchaseOrderVoucherRelationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
@Service
@Transactional
public class PurchaseOrderVoucherRelationExtendedService  extends PurchaseOrderVoucherRelationService {
    private final PurchaseOrderVoucherRelationExtendedRepository purchaseOrderVoucherRelationExtendedRepository;

    public PurchaseOrderVoucherRelationExtendedService(PurchaseOrderVoucherRelationRepository purchaseOrderVoucherRelationRepository, PurchaseOrderVoucherRelationMapper purchaseOrderVoucherRelationMapper, PurchaseOrderVoucherRelationSearchRepository purchaseOrderVoucherRelationSearchRepository, PurchaseOrderVoucherRelationExtendedRepository purchaseOrderVoucherRelationExtendedRepository) {
        super(purchaseOrderVoucherRelationRepository, purchaseOrderVoucherRelationMapper, purchaseOrderVoucherRelationSearchRepository);
        this.purchaseOrderVoucherRelationExtendedRepository = purchaseOrderVoucherRelationExtendedRepository;
    }

    @Override
    public PurchaseOrderVoucherRelationDTO save(PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO) {
        purchaseOrderVoucherRelationDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        purchaseOrderVoucherRelationDTO.setModifiedOn(LocalDate.now());
        return super.save(purchaseOrderVoucherRelationDTO);
    }

    public void storePurchaseOrderVoucherRelation(String voucherNo, Long applicationId, Long id, String voucherName){
        if(!this.purchaseOrderVoucherRelationExtendedRepository.existsByVoucherNo(voucherNo)){
            PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO = new PurchaseOrderVoucherRelationDTO();
            purchaseOrderVoucherRelationDTO.setPurchaseOrderId(applicationId);
            purchaseOrderVoucherRelationDTO.setVoucherName(voucherName);
            purchaseOrderVoucherRelationDTO.setVoucherNo(voucherNo);
            save(purchaseOrderVoucherRelationDTO);
        }

    }
}
