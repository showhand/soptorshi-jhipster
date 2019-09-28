package org.soptorshi.service.extended;

import org.soptorshi.repository.FinancialAccountYearRepository;
import org.soptorshi.repository.search.FinancialAccountYearSearchRepository;
import org.soptorshi.service.FinancialAccountYearService;
import org.soptorshi.service.dto.FinancialAccountYearDTO;
import org.soptorshi.service.mapper.FinancialAccountYearMapper;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinancialAccountYearExtendedService extends FinancialAccountYearService {

    public FinancialAccountYearExtendedService(FinancialAccountYearRepository financialAccountYearRepository, FinancialAccountYearMapper financialAccountYearMapper, FinancialAccountYearSearchRepository financialAccountYearSearchRepository) {
        super(financialAccountYearRepository, financialAccountYearMapper, financialAccountYearSearchRepository);
    }

    @Override
    public FinancialAccountYearDTO save(FinancialAccountYearDTO financialAccountYearDTO) {
        financialAccountYearDTO.setDurationStr(SoptorshiUtils.formatDate(financialAccountYearDTO.getStartDate(),"dd-MM-yyyy")+"- "+ SoptorshiUtils.formatDate(financialAccountYearDTO.getEndDate(),"dd-MM-yyyy"));
        return super.save(financialAccountYearDTO);
    }
}
