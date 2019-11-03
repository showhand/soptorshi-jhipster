package org.soptorshi.service.extended;

import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.AccountType;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.CurrencyFlag;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.repository.*;
import org.soptorshi.repository.extended.AccountBalanceExtendedRepository;
import org.soptorshi.repository.extended.CurrencyExtendedRepository;
import org.soptorshi.repository.extended.FinancialAccountYearExtendedRepository;
import org.soptorshi.repository.extended.SystemAccountMapExtendedRepository;
import org.soptorshi.repository.search.MstAccountSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.MstAccountService;
import org.soptorshi.service.dto.MstAccountDTO;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.soptorshi.service.mapper.MstAccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MstAccountExtendedService extends MstAccountService {
    FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository;
    FinancialAccountYearExtendedService financialAccountYearExtendedService;
    AccountBalanceExtendedRepository accountBalanceExtendedRepository;
    AccountBalanceExtendedService accountBalanceExtendedService;
    CurrencyExtendedRepository currencyExtendedRepository;
    SystemAccountMapExtendedRepository systemAccountMapExtendedRepository;
    JournalVoucherGeneratorRepository journalVoucherGeneratorRepository;
    DtTransactionRepository dtTransactionRepository;
    DtTransactionExtendedService dtTransactionExtendedService;
    DtTransactionMapper dtTransactionMapper;
    JournalVoucherRepository journalVoucherRepository;
    MstAccountMapper mstAccountMapper;

    public MstAccountExtendedService(MstAccountRepository mstAccountRepository, MstAccountMapper mstAccountMapper, MstAccountSearchRepository mstAccountSearchRepository, FinancialAccountYearExtendedRepository financialAccountYearExtendedRepository, FinancialAccountYearExtendedService financialAccountYearExtendedService, AccountBalanceExtendedRepository accountBalanceExtendedRepository, AccountBalanceExtendedService accountBalanceExtendedService, CurrencyExtendedRepository currencyExtendedRepository, SystemAccountMapExtendedRepository systemAccountMapExtendedRepository, JournalVoucherGeneratorRepository journalVoucherGeneratorRepository, DtTransactionRepository dtTransactionRepository, DtTransactionExtendedService dtTransactionExtendedService, DtTransactionMapper dtTransactionMapper, JournalVoucherRepository journalVoucherRepository, MstAccountMapper mstAccountMapper1) {
        super(mstAccountRepository, mstAccountMapper, mstAccountSearchRepository);
        this.financialAccountYearExtendedRepository = financialAccountYearExtendedRepository;
        this.financialAccountYearExtendedService = financialAccountYearExtendedService;
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
        this.accountBalanceExtendedService = accountBalanceExtendedService;
        this.currencyExtendedRepository = currencyExtendedRepository;
        this.systemAccountMapExtendedRepository = systemAccountMapExtendedRepository;
        this.journalVoucherGeneratorRepository = journalVoucherGeneratorRepository;
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionExtendedService = dtTransactionExtendedService;
        this.dtTransactionMapper = dtTransactionMapper;
        this.journalVoucherRepository = journalVoucherRepository;
        this.mstAccountMapper = mstAccountMapper1;
    }

    @Override
    public MstAccountDTO save(MstAccountDTO mstAccountDTO) {
        boolean newAccount = mstAccountDTO.getId()==null?true: false;
        mstAccountDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        mstAccountDTO.setModifiedOn(LocalDate.now());
        mstAccountDTO = super.save(mstAccountDTO);
        if(newAccount){
            accountBalanceExtendedService.createAccountBalanceForNewAccount(mstAccountDTO);
        }
        if(mstAccountDTO.getYearOpenBalance().compareTo(BigDecimal.ZERO)>0)
            createOpeningBalanceTransaction(mstAccountDTO);
        return mstAccountDTO;
    }

    private void createOpeningBalanceTransaction(MstAccountDTO mstAccountDTO){
        AccountBalance accountBalance = accountBalanceExtendedRepository.findByFinancialAccountYear_StatusAndAccount_Id(FinancialYearStatus.ACTIVE, mstAccountDTO.getId());
        Currency baseCurrency = currencyExtendedRepository.findByFlag(CurrencyFlag.BASE);
        MstAccount openingBalanceAdjustmentAccount = systemAccountMapExtendedRepository.findByAccountType(AccountType.OPENING_BALANCE_ADJUSTMENT_ACCOUNT).getAccount();
        if(accountBalance.getYearOpenBalance().compareTo(BigDecimal.ZERO)>0  ){
            reverseOpeningBalanceTransaction(accountBalance, baseCurrency, openingBalanceAdjustmentAccount);
        }
        createOpeningBalanceTransaction(mstAccountMapper.toEntity(mstAccountDTO), accountBalance, baseCurrency, openingBalanceAdjustmentAccount);
    }

    private void createOpeningBalanceTransaction(MstAccount mstAccount, AccountBalance accountBalance, Currency baseCurrency, MstAccount openingBalanceAdjustmentAccount){
        JournalVoucherGenerator journalVoucherGenerator = journalVoucherGeneratorRepository.save(new JournalVoucherGenerator());
        String voucherNo = "JN"+String.format("%06d", journalVoucherGenerator.getId());
        DtTransaction accountTransaction = new DtTransaction();
        accountTransaction.setVoucherNo(voucherNo);
        accountTransaction.setVoucherDate(LocalDate.now());
        accountTransaction.setPostDate(accountTransaction.getVoucherDate());
        accountTransaction.setAmount(mstAccount.getYearOpenBalance());
        accountTransaction.setBalanceType(mstAccount.getYearOpenBalanceType());
        accountTransaction.setConvFactor(BigDecimal.ONE);
        accountTransaction.setfCurrency(BigDecimal.ONE);
        accountTransaction.setNarration("Opening Balance Adjustment Voucher");
        accountTransaction.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        accountTransaction.setModifiedOn(LocalDate.now());
        accountTransaction.setAccount(mstAccount);
        accountTransaction.setCurrency(baseCurrency);

        DtTransaction openingBalanceAdjustmentTransaction = new DtTransaction();
        openingBalanceAdjustmentTransaction.setVoucherNo(voucherNo);
        openingBalanceAdjustmentTransaction.setVoucherDate(accountTransaction.getVoucherDate());
        openingBalanceAdjustmentTransaction.setPostDate(accountTransaction.getPostDate());
        openingBalanceAdjustmentTransaction.setAmount(mstAccount.getYearOpenBalance());
        openingBalanceAdjustmentTransaction.setBalanceType(mstAccount.getYearOpenBalanceType().equals(BalanceType.DEBIT)? BalanceType.CREDIT: BalanceType.DEBIT);
        openingBalanceAdjustmentTransaction.setConvFactor(BigDecimal.ONE);
        openingBalanceAdjustmentTransaction.setfCurrency(BigDecimal.ONE);
        openingBalanceAdjustmentTransaction.setNarration("Opening Balance Adjustment Voucher");
        openingBalanceAdjustmentTransaction.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        openingBalanceAdjustmentTransaction.setModifiedOn(LocalDate.now());
        openingBalanceAdjustmentTransaction.setAccount(openingBalanceAdjustmentAccount);
        openingBalanceAdjustmentTransaction.setCurrency(baseCurrency);

        List<DtTransaction> allTransactions = new ArrayList<>();
        allTransactions.add(accountTransaction);
        allTransactions.add(openingBalanceAdjustmentTransaction);
        allTransactions = dtTransactionRepository.saveAll(allTransactions);
        dtTransactionExtendedService.updateAccountBalance(dtTransactionMapper.toDto(allTransactions));
        createJournalVoucher(baseCurrency, voucherNo, accountTransaction);

        accountBalance.setYearOpenBalance(mstAccount.getYearOpenBalance());
        accountBalance.setYearOpenBalanceType(mstAccount.getYearOpenBalanceType());
        accountBalance.setModifiedBy(accountTransaction.getModifiedBy());
        accountBalance.setModifiedOn(accountTransaction.getModifiedOn());
        accountBalanceExtendedRepository.save(accountBalance);
    }

    private void createJournalVoucher(Currency baseCurrency, String voucherNo, DtTransaction accountTransaction) {
        JournalVoucher journalVoucher = new JournalVoucher();
        journalVoucher.setCurrency(baseCurrency);
        journalVoucher.setVoucherNo(voucherNo);
        journalVoucher.setConversionFactor(BigDecimal.ONE);
        journalVoucher.setVoucherDate(accountTransaction.getVoucherDate());
        journalVoucher.setModifiedBy(accountTransaction.getModifiedBy());
        journalVoucher.setModifiedOn(accountTransaction.getModifiedOn());
        journalVoucher.setPostDate(accountTransaction.getPostDate());
        journalVoucherRepository.save(journalVoucher);
    }

    private void reverseOpeningBalanceTransaction(AccountBalance accountBalance, Currency baseCurrency, MstAccount openingBalanceAdjustmentAccount){
        JournalVoucherGenerator journalVoucherGenerator = journalVoucherGeneratorRepository.save(new JournalVoucherGenerator());
        String voucherNo = "JN"+String.format("%06d", journalVoucherGenerator.getId());
        DtTransaction accountTransaction = new DtTransaction();
        accountTransaction.setVoucherNo(voucherNo);
        accountTransaction.setVoucherDate(LocalDate.now());
        accountTransaction.setPostDate(accountTransaction.getVoucherDate());
        accountTransaction.setAmount(accountBalance.getYearOpenBalance());
        accountTransaction.setBalanceType(accountBalance.getYearOpenBalanceType().equals(BalanceType.DEBIT)? BalanceType.CREDIT: BalanceType.DEBIT);
        accountTransaction.setConvFactor(BigDecimal.ONE);
        accountTransaction.setfCurrency(BigDecimal.ONE);
        accountTransaction.setNarration("Opening Balance Adjustment Voucher");
        accountTransaction.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        accountTransaction.setModifiedOn(LocalDate.now());
        accountTransaction.setAccount(accountBalance.getAccount());
        accountTransaction.setCurrency(baseCurrency);

        DtTransaction openingBalanceAdjustmentTransaction = new DtTransaction();
        openingBalanceAdjustmentTransaction.setVoucherNo(voucherNo);
        openingBalanceAdjustmentTransaction.setVoucherDate(accountTransaction.getVoucherDate());
        openingBalanceAdjustmentTransaction.setPostDate(accountTransaction.getPostDate());
        openingBalanceAdjustmentTransaction.setAmount(accountBalance.getYearOpenBalance());
        openingBalanceAdjustmentTransaction.setBalanceType(accountBalance.getYearOpenBalanceType());
        openingBalanceAdjustmentTransaction.setConvFactor(BigDecimal.ONE);
        openingBalanceAdjustmentTransaction.setfCurrency(BigDecimal.ONE);
        openingBalanceAdjustmentTransaction.setNarration("Opening Balance Adjustment Voucher");
        openingBalanceAdjustmentTransaction.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        openingBalanceAdjustmentTransaction.setModifiedOn(LocalDate.now());
        openingBalanceAdjustmentTransaction.setAccount(openingBalanceAdjustmentAccount);
        openingBalanceAdjustmentTransaction.setCurrency(baseCurrency);

        List<DtTransaction> allTransactions = new ArrayList<>();
        allTransactions.add(accountTransaction);
        allTransactions.add(openingBalanceAdjustmentTransaction);
        allTransactions = dtTransactionRepository.saveAll(allTransactions);
        dtTransactionExtendedService.updateAccountBalance(dtTransactionMapper.toDto(allTransactions));

        createJournalVoucher(baseCurrency, voucherNo, accountTransaction);
    }
}
