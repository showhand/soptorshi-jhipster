package org.soptorshi.mediator;

import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.CurrencyFlag;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.repository.DepreciationMapRepository;
import org.soptorshi.repository.extended.*;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.extended.DtTransactionExtendedService;
import org.soptorshi.service.extended.JournalVoucherExtendedService;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VoucherTransactionService {
    private final JournalVoucherExtendedService journalVoucherExtendedService;
    private final JournalVoucherExtendedRepository journalVoucherExtendedRepository;
    private final DepreciationMapRepository depreciationMapRepository;
    private final MstGroupExtendedRepository groupExtendedRepository;
    private final MstAccountExtendedRepository mstAccountExtendedRepository;
    private final CurrencyExtendedRepository currencyExtendedRepository;
    private final AccountBalanceExtendedRepository accountBalanceExtendedRepository;
    private final DtTransactionExtendedService dtTransactionExtendedService;
    private final DtTransactionExtendedRepository dtTransactionExtendedRepository;
    private final DtTransactionMapper dtTransactionMapper;
    private final FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;
    private final EntityManager entityManager;

    public VoucherTransactionService(JournalVoucherExtendedService journalVoucherExtendedService, JournalVoucherExtendedRepository journalVoucherExtendedRepository, DepreciationMapRepository depreciationMapRepository, MstGroupExtendedRepository groupExtendedRepository, MstAccountExtendedRepository mstAccountExtendedRepository, CurrencyExtendedRepository currencyExtendedRepository, AccountBalanceExtendedRepository accountBalanceExtendedRepository, DtTransactionExtendedService dtTransactionExtendedService, DtTransactionExtendedRepository dtTransactionExtendedRepository, DtTransactionMapper dtTransactionMapper, FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository, EntityManager entityManager) {
        this.journalVoucherExtendedService = journalVoucherExtendedService;
        this.journalVoucherExtendedRepository = journalVoucherExtendedRepository;
        this.depreciationMapRepository = depreciationMapRepository;
        this.groupExtendedRepository = groupExtendedRepository;
        this.mstAccountExtendedRepository = mstAccountExtendedRepository;
        this.currencyExtendedRepository = currencyExtendedRepository;
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
        this.dtTransactionExtendedService = dtTransactionExtendedService;
        this.dtTransactionExtendedRepository = dtTransactionExtendedRepository;
        this.dtTransactionMapper = dtTransactionMapper;
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
        this.entityManager = entityManager;
    }

    public void calculateDepreciation(MonthType monthType, Long financialAccountYearId){

        FinancialAccountYear financialAccountYear = financialAccountYearExtendedRepository.getOne(financialAccountYearId);
        List<DepreciationMap> depreciationMapList = depreciationMapRepository.findAll();

        JournalVoucherDTO journalVoucherDTO = createJournalVoucher(monthType, financialAccountYear);

        createDepreciatinTransactions(monthType, financialAccountYear, depreciationMapList, journalVoucherDTO);

    }

    private void createDepreciatinTransactions(MonthType monthType, FinancialAccountYear financialAccountYear, List<DepreciationMap> depreciationMapList, JournalVoucherDTO journalVoucherDTO) {
        List<DtTransactionDTO> transactionDTOS = new ArrayList<>();

        for(DepreciationMap depreciationMap: depreciationMapList){
            DtTransactionDTO drTransaction = new DtTransactionDTO();
            drTransaction.setAccountId(depreciationMap.getAccountId());
            drTransaction.setAccountName(depreciationMap.getAccountName());
            AccountBalance accountBalance = accountBalanceExtendedRepository.findByFinancialAccountYear_StatusAndAccount_Id(financialAccountYear.getStatus(), depreciationMap.getAccountId());
            BigDecimal percentage = accountBalance.getAccount().getDepreciationRate().divide(new BigDecimal(100));
            drTransaction.setAmount(accountBalance.getTotDebitTrans().multiply(percentage));
            drTransaction.setBalanceType(BalanceType.DEBIT);
//            drTransaction.setVoucherId(journalVoucherDTO.getId());
            drTransaction.setVoucherNo(journalVoucherDTO.getVoucherNo());
            drTransaction.setVoucherDate(journalVoucherDTO.getVoucherDate());
            transactionDTOS.add(drTransaction);
            dtTransactionExtendedRepository.saveAndFlush(dtTransactionMapper.toEntity(drTransaction));

            DtTransactionDTO crTransaction = new DtTransactionDTO();
            crTransaction.setAccountId(depreciationMap.getDepreciationAccountId());
            crTransaction.setAccountName(depreciationMap.getDepreciationAccountName());
            crTransaction.setAmount(drTransaction.getAmount());
            crTransaction.setBalanceType(BalanceType.CREDIT);
            crTransaction.setVoucherDate(journalVoucherDTO.getVoucherDate());
//            crTransaction.setVoucherId(journalVoucherDTO.getId());
            crTransaction.setVoucherNo(journalVoucherDTO.getVoucherNo());
            dtTransactionExtendedRepository.saveAndFlush(dtTransactionMapper.toEntity(crTransaction));
            transactionDTOS.add(crTransaction);
        }




        entityManager.flush();

        // this is for posting
        journalVoucherDTO.setPostDate(journalVoucherDTO.getVoucherDate());
        journalVoucherExtendedService.save(journalVoucherDTO);
    }

    private LocalDate getTempDate(MonthType monthType, FinancialAccountYear financialAccountYear){
        int year = financialAccountYear.getStartDate().getYear();
        if(monthType.ordinal()>0 && monthType.ordinal()<=5)
            year = financialAccountYear.getEndDate().getYear();

        Month month = Month.valueOf(monthType.toString());
        LocalDate firstTempDate = LocalDate.of(year, month, 1);
        return firstTempDate;
    }


    public JournalVoucherDTO createJournalVoucher(MonthType monthType, FinancialAccountYear financialAccountYear){
        JournalVoucherDTO journalVoucherDTO = new JournalVoucherDTO();
        journalVoucherDTO.setConversionFactor(BigDecimal.ONE);
        LocalDate firstTempDate = getTempDate(monthType, financialAccountYear);
        LocalDate voucherAndPostDate = LocalDate.of(firstTempDate.getYear(), firstTempDate.getMonth(), firstTempDate.lengthOfMonth());

        journalVoucherDTO.setVoucherDate(voucherAndPostDate);

        Currency currency = currencyExtendedRepository.findByFlag(CurrencyFlag.BASE);
        journalVoucherDTO.setCurrencyId(currency.getId());
        journalVoucherDTO.setCurrencyNotation(currency.getNotation());
        journalVoucherDTO = journalVoucherExtendedService.save(journalVoucherDTO);
        return journalVoucherDTO;
    }
}
