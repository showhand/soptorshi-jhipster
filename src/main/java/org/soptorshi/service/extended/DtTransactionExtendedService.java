package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LongFilter;
import org.apache.commons.lang3.ObjectUtils;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.repository.AccountBalanceRepository;
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.MonthlyBalanceRepository;
import org.soptorshi.repository.extended.FinancialAccountYearExtendedRepository;
import org.soptorshi.repository.search.DtTransactionSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.AccountBalanceQueryService;
import org.soptorshi.service.DtTransactionService;
import org.soptorshi.service.MonthlyBalanceQueryService;
import org.soptorshi.service.dto.*;
import org.soptorshi.service.mapper.AccountBalanceMapper;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.soptorshi.service.mapper.MonthlyBalanceMapper;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class DtTransactionExtendedService extends DtTransactionService {
    private AccountBalanceQueryService accountBalanceQueryService;
    private MonthlyBalanceQueryService monthlyBalanceQueryService;
    private FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;
    private AccountBalanceRepository accountBalanceRepository;
    private MonthlyBalanceRepository monthlyBalanceRepository;
    private AccountBalanceMapper accountBalanceMapper;
    private MonthlyBalanceMapper monthlyBalanceMapper;

    public DtTransactionExtendedService(DtTransactionRepository dtTransactionRepository, DtTransactionMapper dtTransactionMapper, DtTransactionSearchRepository dtTransactionSearchRepository, AccountBalanceQueryService accountBalanceQueryService, MonthlyBalanceQueryService monthlyBalanceQueryService, FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository, AccountBalanceRepository accountBalanceRepository, MonthlyBalanceRepository monthlyBalanceRepository, AccountBalanceMapper accountBalanceMapper, MonthlyBalanceMapper monthlyBalanceMapper) {
        super(dtTransactionRepository, dtTransactionMapper, dtTransactionSearchRepository);
        this.accountBalanceQueryService = accountBalanceQueryService;
        this.monthlyBalanceQueryService = monthlyBalanceQueryService;
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
        this.accountBalanceRepository = accountBalanceRepository;
        this.monthlyBalanceRepository = monthlyBalanceRepository;
        this.accountBalanceMapper = accountBalanceMapper;
        this.monthlyBalanceMapper = monthlyBalanceMapper;
    }


    @Override
    public DtTransactionDTO save(DtTransactionDTO dtTransactionDTO) {
        return super.save(dtTransactionDTO);
    }

    /*
     * This method will update AccountBalance and MonthlyBalance during transaction update. It should be called only once when any voucher is posted.
     *
     * Summary of the steps:
     * 1. Fetch currently opened financial account year. [it will help in fetching account balance list based on voucher account ids, as account balance has dependency on financial account year.]
     * 2. Make a list of distinct account Ids which are in the vouchers.
     * 3. Fetch account balance list based on opened financial account year and account id list. [Here criteria is used]
     * 4. Make list of account balance ids. [Monthly balance has dependencies on account balance id]
     * 5. Fetch monthly balances based on the account balance ids.
     * 6. Create accountId+monthType map with monthly balance.
     * 7. Update account balances and monthly balances as the balance type of the vouchers.
     * */
    public void updateAccountBalance(List<DtTransactionDTO> transactionDTOList) {
        FinancialAccountYear openedFinancialAccountYear = financialAccountYearExtendedRepository.getByStatus(FinancialYearStatus.ACTIVE);

        List<Long> accountIds = transactionDTOList.stream().map(t -> t.getAccountId()).collect(Collectors.toList());


        // block 1
        LongFilter accountIdFilter = new LongFilter();
        accountIdFilter.setIn(accountIds);

        LongFilter financialIdFilter = new LongFilter();
        financialIdFilter.setEquals(openedFinancialAccountYear.getId());

        AccountBalanceCriteria accountBalanceCriteria = new AccountBalanceCriteria();
        accountBalanceCriteria.setAccountId(accountIdFilter);
        accountBalanceCriteria.setFinancialAccountYearId(financialIdFilter);

        List<AccountBalanceDTO> accountBalanceDTOS = accountBalanceQueryService.findByCriteria(accountBalanceCriteria);
        Map<Long, AccountBalanceDTO> accountMapWithAccountBalance = accountBalanceDTOS.stream().collect(Collectors.toMap(a -> a.getAccountId(), a -> a));
        // end of block 1


        // block 2
        List<Long> accountBalanceIds = accountBalanceDTOS.stream().map(a -> a.getId()).collect(Collectors.toList());
        LongFilter accountBalanceIdFilter = new LongFilter();
        accountBalanceIdFilter.setIn(accountBalanceIds);

        MonthlyBalanceCriteria.MonthTypeFilter monthTypeFilter = new MonthlyBalanceCriteria.MonthTypeFilter();
        monthTypeFilter.setEquals(SoptorshiUtils.getMonthType(transactionDTOList.get(0).getPostDate().getMonth()));

        MonthlyBalanceCriteria monthlyBalanceCriteria = new MonthlyBalanceCriteria();
        monthlyBalanceCriteria.setAccountBalanceId(accountBalanceIdFilter);
        monthlyBalanceCriteria.setMonthType(monthTypeFilter);

        List<MonthlyBalanceDTO> monthlyBalanceDTOS = monthlyBalanceQueryService.findByCriteria(monthlyBalanceCriteria);
        Map<String, MonthlyBalanceDTO> accountBalanceAndMonthTypeMapWithMonthlyBalance = monthlyBalanceDTOS.stream()
            .collect(Collectors.toMap(m -> m.getAccountBalanceId() + "" + SoptorshiUtils.getMonthType(), m -> m));
        // end of block 2

        List<MonthlyBalanceDTO> newMonthlyBalanceDtos = new ArrayList<>();
        for (DtTransactionDTO dtTransactionDTO : transactionDTOList) {
            //account balance section
            AccountBalanceDTO accountBalanceDTO = accountMapWithAccountBalance.get(dtTransactionDTO.getAccountId());
            if (dtTransactionDTO.getBalanceType().equals(BalanceType.DEBIT)) {
                BigDecimal totalDebt = ObjectUtils.defaultIfNull(accountBalanceDTO.getTotDebitTrans(), BigDecimal.ZERO);
                BigDecimal totalAmount = totalDebt
                    .add(dtTransactionDTO
                        .getAmount()
                        .multiply(
                            dtTransactionDTO.getConvFactor()
                        ));
                accountBalanceDTO.setTotDebitTrans(totalAmount);
            } else {
                BigDecimal totalCredit = ObjectUtils.defaultIfNull(accountBalanceDTO.getTotCreditTrans(), BigDecimal.ZERO);
                BigDecimal totalAmount = totalCredit.add(dtTransactionDTO.getAmount().multiply(dtTransactionDTO.getConvFactor()));
                accountBalanceDTO.setTotCreditTrans(totalAmount);
            }
            // end of account balance section

            // monthly balance section
            if (accountBalanceAndMonthTypeMapWithMonthlyBalance.containsKey(accountBalanceDTO.getId() + "" + SoptorshiUtils.getMonthType(dtTransactionDTO.getPostDate().getMonth()))) {
                MonthlyBalanceDTO monthlyBalance = accountBalanceAndMonthTypeMapWithMonthlyBalance.get(accountBalanceDTO.getId() + "" + SoptorshiUtils.getMonthType(dtTransactionDTO.getPostDate().getMonth()));
                monthlyBalance.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
                monthlyBalance.setModifiedOn(LocalDate.now());
                if (dtTransactionDTO.getBalanceType().equals(BalanceType.DEBIT))
                    monthlyBalance.setTotMonthDbBal(monthlyBalance.getTotMonthDbBal().add(dtTransactionDTO.getAmount().multiply(dtTransactionDTO.getConvFactor())));
                else
                    monthlyBalance.setTotMonthCrBal(monthlyBalance.getTotMonthCrBal().add(dtTransactionDTO.getAmount().multiply(dtTransactionDTO.getConvFactor())));
            } else {
                MonthlyBalanceDTO monthlyBalanceDTO = new MonthlyBalanceDTO();
                monthlyBalanceDTO.setAccountBalanceId(accountBalanceDTO.getId());
                monthlyBalanceDTO.setMonthType(SoptorshiUtils.getMonthType(dtTransactionDTO.getPostDate().getMonth()));
                if (dtTransactionDTO.getBalanceType().equals(BalanceType.DEBIT)) {
                    monthlyBalanceDTO.setTotMonthDbBal(dtTransactionDTO.getAmount().multiply(dtTransactionDTO.getConvFactor()));
                    monthlyBalanceDTO.setTotMonthCrBal(BigDecimal.ZERO);
                } else {
                    monthlyBalanceDTO.setTotMonthCrBal(dtTransactionDTO.getAmount().multiply(dtTransactionDTO.getConvFactor()));
                    monthlyBalanceDTO.setTotMonthDbBal(BigDecimal.ZERO);
                }
                monthlyBalanceDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
                monthlyBalanceDTO.setModifiedOn(LocalDate.now());
                newMonthlyBalanceDtos.add(monthlyBalanceDTO);
            }
            // end of monthly balance section
        }

        accountBalanceRepository.saveAll(accountBalanceMapper.toEntity(accountBalanceDTOS));
        monthlyBalanceRepository.saveAll(monthlyBalanceMapper.toEntity(monthlyBalanceDTOS));
        monthlyBalanceRepository.saveAll(monthlyBalanceMapper.toEntity(newMonthlyBalanceDtos));
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }
}
