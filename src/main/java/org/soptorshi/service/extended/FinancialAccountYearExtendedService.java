package org.soptorshi.service.extended;

import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.repository.FinancialAccountYearRepository;
import org.soptorshi.repository.extended.FinancialAccountYearExtendedRepository;
import org.soptorshi.repository.search.FinancialAccountYearSearchRepository;
import org.soptorshi.service.FinancialAccountYearService;
import org.soptorshi.service.dto.FinancialAccountYearDTO;
import org.soptorshi.service.mapper.FinancialAccountYearMapper;
import org.soptorshi.utils.SoptorshiUtils;
import org.soptorshi.web.rest.errors.CustomParameterizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FinancialAccountYearExtendedService extends FinancialAccountYearService {
    private FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;

    public FinancialAccountYearExtendedService(FinancialAccountYearRepository financialAccountYearRepository,
                                               FinancialAccountYearMapper financialAccountYearMapper,
                                               FinancialAccountYearSearchRepository financialAccountYearSearchRepository,
                                               FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository) {
        super(financialAccountYearRepository, financialAccountYearMapper, financialAccountYearSearchRepository);
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
    }

    @Override
    public FinancialAccountYearDTO save(FinancialAccountYearDTO financialAccountYearDTO) {
        financialAccountYearDTO.setDurationStr(SoptorshiUtils.formatDate(financialAccountYearDTO.getStartDate(),"dd-MM-yyyy")+"- "+ SoptorshiUtils.formatDate(financialAccountYearDTO.getEndDate(),"dd-MM-yyyy"));
        return super.save(financialAccountYearDTO);
    }

}
