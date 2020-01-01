package org.soptorshi.service.extended;

import org.soptorshi.repository.PurchaseOrderRepository;
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
    public PurchaseOrderExtendedService(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderMapper purchaseOrderMapper, PurchaseOrderSearchRepository purchaseOrderSearchRepository) {
        super(purchaseOrderRepository, purchaseOrderMapper, purchaseOrderSearchRepository);
    }

    @Override
    public PurchaseOrderDTO save(PurchaseOrderDTO purchaseOrderDTO) {
        purchaseOrderDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        purchaseOrderDTO.setModifiedOn(LocalDate.now());
        return super.save(purchaseOrderDTO);
    }
}
