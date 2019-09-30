package org.soptorshi.service.extended;

import org.soptorshi.domain.AccountBalance;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.extended.AccountBalanceExtendedRepository;
import org.soptorshi.repository.extended.FinancialAccountYearExtendedRepository;
import org.soptorshi.repository.search.MstAccountSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.MstAccountService;
import org.soptorshi.service.dto.MstAccountDTO;
import org.soptorshi.service.mapper.MstAccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class MstAccountExtendedService extends MstAccountService {
    FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;
    FinancialAccountYearExtendedService financialAccountYearExtendedService;
    AccountBalanceExtendedRepository accountBalanceExtendedRepository;

    public MstAccountExtendedService(MstAccountRepository mstAccountRepository,
                                     MstAccountMapper mstAccountMapper,
                                     MstAccountSearchRepository mstAccountSearchRepository,
                                     FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository,
                                     AccountBalanceExtendedRepository accountBalanceExtendedRepository,
                                     FinancialAccountYearExtendedService financialAccountYearExtendedService) {
        super(mstAccountRepository, mstAccountMapper, mstAccountSearchRepository);
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
        this.financialAccountYearExtendedService = financialAccountYearExtendedService;
    }

    @Override
    public MstAccountDTO save(MstAccountDTO mstAccountDTO) throws Exception{
        mstAccountDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        mstAccountDTO.setModifiedOn(LocalDate.now());
        FinancialAccountYear financialAccountYear = financialAccountYearExtendedService.getOpenedYear();
//        if(accountBalanceExtendedRepository.existsAccountBalanceByAccountAndFinancialAccountYear(a))
        
        return super.save(mstAccountDTO);
    }
}
