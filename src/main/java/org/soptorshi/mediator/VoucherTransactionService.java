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
    private final DepreciationMapRepository depreciationMapRepository;
    private final MstGroupExtendedRepository groupExtendedRepository;
    private final MstAccountExtendedRepository mstAccountExtendedRepository;
    private final CurrencyExtendedRepository currencyExtendedRepository;
    private final AccountBalanceExtendedRepository accountBalanceExtendedRepository;
    private final DtTransactionExtendedService dtTransactionExtendedService;
    private final DtTransactionExtendedRepository dtTransactionExtendedRepository;
    private final DtTransactionMapper dtTransactionMapper;
    private final FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;

    public VoucherTransactionService(JournalVoucherExtendedService journalVoucherExtendedService, DepreciationMapRepository depreciationMapRepository, MstGroupExtendedRepository groupExtendedRepository, MstAccountExtendedRepository mstAccountExtendedRepository, CurrencyExtendedRepository currencyExtendedRepository, AccountBalanceExtendedRepository accountBalanceExtendedRepository, DtTransactionExtendedService dtTransactionExtendedService, DtTransactionExtendedRepository dtTransactionExtendedRepository, DtTransactionMapper dtTransactionMapper, FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository) {
        this.journalVoucherExtendedService = journalVoucherExtendedService;
        this.depreciationMapRepository = depreciationMapRepository;
        this.groupExtendedRepository = groupExtendedRepository;
        this.mstAccountExtendedRepository = mstAccountExtendedRepository;
        this.currencyExtendedRepository = currencyExtendedRepository;
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
        this.dtTransactionExtendedService = dtTransactionExtendedService;
        this.dtTransactionExtendedRepository = dtTransactionExtendedRepository;
        this.dtTransactionMapper = dtTransactionMapper;
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
    }

    public void calculateDepreciation(MonthType monthType, Long financialAccountYearId){

        FinancialAccountYear financialAccountYear = financialAccountYearExtendedRepository.getOne(financialAccountYearId);
        List<DepreciationMap> depreciationMapList = depreciationMapRepository.findAll();

        JournalVoucherDTO journalVoucherDTO = createJournalVoucher(monthType, financialAccountYear);

        List<DtTransactionDTO> transactionDTOS = new ArrayList<>();

        for(DepreciationMap depreciationMap: depreciationMapList){
            DtTransactionDTO drTransaction = new DtTransactionDTO();
            drTransaction.setAccountId(depreciationMap.getAccountId());
            drTransaction.setAccountName(depreciationMap.getAccountName());
            AccountBalance accountBalance = accountBalanceExtendedRepository.findByFinancialAccountYear_StatusAndAccount_Id(financialAccountYear.getStatus(), depreciationMap.getAccountId());
            BigDecimal percentage = accountBalance.getAccount().getDepreciationRate().divide(new BigDecimal(100));
            drTransaction.setAmount(accountBalance.getTotCreditTrans().multiply(percentage));
            drTransaction.setBalanceType(BalanceType.DEBIT);
            drTransaction.setVoucherId(journalVoucherDTO.getId());
            transactionDTOS.add(drTransaction);

            DtTransactionDTO crTransaction = new DtTransactionDTO();
            crTransaction.setAccountId(depreciationMap.getDepreciationAccountId());
            crTransaction.setAccountName(depreciationMap.getDepreciationAccountName());
            crTransaction.setAmount(drTransaction.getAmount());
            crTransaction.setBalanceType(BalanceType.CREDIT);
            crTransaction.setVoucherId(journalVoucherDTO.getId());
            transactionDTOS.add(crTransaction);
        }

        dtTransactionExtendedRepository.saveAll(dtTransactionMapper.toEntity(transactionDTOS));
        dtTransactionExtendedRepository.flush();

        // this is for posting
        LocalDate postDate = getTempDate(monthType, financialAccountYear);
        journalVoucherDTO.setPostDate(postDate);
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
        return journalVoucherExtendedService.save(journalVoucherDTO);
    }
}
