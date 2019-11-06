package org.soptorshi.service.extended;

import org.soptorshi.domain.AccountBalance;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.repository.AccountBalanceRepository;
import org.soptorshi.repository.extended.AccountBalanceExtendedRepository;
import org.soptorshi.repository.extended.FinancialAccountYearExtendedRepository;
import org.soptorshi.repository.search.AccountBalanceSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.AccountBalanceService;
import org.soptorshi.service.dto.AccountBalanceDTO;
import org.soptorshi.service.dto.MstAccountDTO;
import org.soptorshi.service.mapper.AccountBalanceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
public class AccountBalanceExtendedService extends AccountBalanceService {

    private AccountBalanceExtendedRepository accountBalanceExtendedRepository;
    private MonthlyBalanceExtendedService monthlyBalanceExtendedService;
    private FinancialAccountYearExtendedService financialAccountYearExtendedService;
    private FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;

    public AccountBalanceExtendedService(AccountBalanceRepository accountBalanceRepository,
                                         AccountBalanceMapper accountBalanceMapper,
                                         AccountBalanceSearchRepository accountBalanceSearchRepository,
                                         AccountBalanceExtendedRepository accountBalanceExtendedRepository,
                                         MonthlyBalanceExtendedService monthlyBalanceExtendedService,
                                         FinancialAccountYearExtendedService financialAccountYearExtendedService,
                                         FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository) {
        super(accountBalanceRepository, accountBalanceMapper, accountBalanceSearchRepository);
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
        this.monthlyBalanceExtendedService = monthlyBalanceExtendedService;
        this.financialAccountYearExtendedService = financialAccountYearExtendedService;
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
    }

    @Override
    public AccountBalanceDTO save(AccountBalanceDTO accountBalanceDTO) {
        accountBalanceDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        accountBalanceDTO.setModifiedOn(LocalDate.now());
        return super.save(accountBalanceDTO);
    }

    public AccountBalanceDTO createAccountBalanceForNewAccount(MstAccountDTO mstAccount){
        AccountBalanceDTO accountBalance = new AccountBalanceDTO();
        accountBalance.setAccountId(mstAccount.getId());
        accountBalance.setYearOpenBalance(BigDecimal.ZERO);
        accountBalance.setYearOpenBalanceType(BalanceType.DEBIT);
        FinancialAccountYear openedFinancialAccountYear = financialAccountYearExtendedRepository.getByStatus(FinancialYearStatus.ACTIVE);
        accountBalance.setFinancialAccountYearId(openedFinancialAccountYear.getId());
        if(mstAccount.getYearOpenBalanceType().equals(BalanceType.DEBIT)){
            accountBalance.setTotDebitTrans(mstAccount.getYearOpenBalance());
            accountBalance.setTotCreditTrans(BigDecimal.ZERO);
        }else{
            accountBalance.setTotCreditTrans(mstAccount.getYearCloseBalance());
            accountBalance.setTotCreditTrans(BigDecimal.ZERO);
        }
        accountBalance.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        accountBalance.setModifiedOn(LocalDate.now());

        accountBalance = save(accountBalance);
        monthlyBalanceExtendedService.createMonthlyBalanceForNewAccount(accountBalance, mstAccount);
        return accountBalance;
    }




}
