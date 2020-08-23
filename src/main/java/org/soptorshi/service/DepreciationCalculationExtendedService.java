package org.soptorshi.service;

import org.soptorshi.mediator.VoucherTransactionService;
import org.soptorshi.repository.DepreciationCalculationRepository;
import org.soptorshi.repository.search.DepreciationCalculationSearchRepository;
import org.soptorshi.service.dto.DepreciationCalculationDTO;
import org.soptorshi.service.mapper.DepreciationCalculationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepreciationCalculationExtendedService  extends DepreciationCalculationService{
    private final VoucherTransactionService voucherTransactionService;


    public DepreciationCalculationExtendedService(DepreciationCalculationRepository depreciationCalculationRepository, DepreciationCalculationMapper depreciationCalculationMapper, DepreciationCalculationSearchRepository depreciationCalculationSearchRepository, VoucherTransactionService voucherTransactionService) {
        super(depreciationCalculationRepository, depreciationCalculationMapper, depreciationCalculationSearchRepository);
        this.voucherTransactionService = voucherTransactionService;
    }

    @Override
    public DepreciationCalculationDTO save(DepreciationCalculationDTO depreciationCalculationDTO) {
        if(depreciationCalculationDTO.isIsExecuted())
            voucherTransactionService.calculateDepreciation(depreciationCalculationDTO.getMonthType(), depreciationCalculationDTO.getFinancialAccountYearId());
        return super.save(depreciationCalculationDTO);
    }
}
