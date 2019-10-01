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
    AccountBalanceExtendedService accountBalanceExtendedService;

    public MstAccountExtendedService(MstAccountRepository mstAccountRepository,
                                     MstAccountMapper mstAccountMapper,
                                     MstAccountSearchRepository mstAccountSearchRepository,
                                     FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository,
                                     AccountBalanceExtendedRepository accountBalanceExtendedRepository,
                                     FinancialAccountYearExtendedService financialAccountYearExtendedService,
                                     AccountBalanceExtendedService accountBalanceExtendedService) {
        super(mstAccountRepository, mstAccountMapper, mstAccountSearchRepository);
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
        this.financialAccountYearExtendedService = financialAccountYearExtendedService;
        this.accountBalanceExtendedService = accountBalanceExtendedService;
    }

    @Override
    public MstAccountDTO save(MstAccountDTO mstAccountDTO) {
        mstAccountDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        mstAccountDTO.setModifiedOn(LocalDate.now());
        mstAccountDTO = super.save(mstAccountDTO);
        if(mstAccountDTO.getId()!=null){
            accountBalanceExtendedService.createAccountBalanceForNewAccount(mstAccountDTO);
        }else{

        }
        return mstAccountDTO;
    }
}
