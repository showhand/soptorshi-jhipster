package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.AccountType;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.CurrencyFlag;
import org.soptorshi.domain.enumeration.VoucherReferenceType;
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.JournalVoucherGeneratorRepository;
import org.soptorshi.repository.JournalVoucherRepository;
import org.soptorshi.repository.SalaryVoucherRelationRepository;
import org.soptorshi.repository.extended.CurrencyExtendedRepository;
import org.soptorshi.repository.extended.SystemAccountMapExtendedRepository;
import org.soptorshi.repository.search.JournalVoucherSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.DtTransactionQueryService;
import org.soptorshi.service.DtTransactionService;
import org.soptorshi.service.JournalVoucherQueryService;
import org.soptorshi.service.JournalVoucherService;
import org.soptorshi.service.dto.DtTransactionCriteria;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.soptorshi.service.mapper.JournalVoucherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class JournalVoucherExtendedService extends JournalVoucherService {
    private JournalVoucherGeneratorRepository journalVoucherGeneratorRepository;
    private DtTransactionQueryService dtTransactionQueryService;
    private DtTransactionMapper dtTransactionMapper;
    private DtTransactionRepository dtTransactionRepository;
    private DtTransactionExtendedService dtTransactionService;
    private CurrencyExtendedRepository currencyExtendedRepository;
    private SalaryVoucherRelationRepository salaryVoucherRelationRepository;
    private SystemAccountMapExtendedRepository systemAccountMapExtendedRepository;

    public JournalVoucherExtendedService(JournalVoucherRepository journalVoucherRepository, JournalVoucherMapper journalVoucherMapper, JournalVoucherSearchRepository journalVoucherSearchRepository, JournalVoucherGeneratorRepository journalVoucherGeneratorRepository, DtTransactionQueryService dtTransactionQueryService, DtTransactionMapper dtTransactionMapper, DtTransactionRepository dtTransactionRepository, DtTransactionExtendedService dtTransactionService, CurrencyExtendedRepository currencyExtendedRepository, SalaryVoucherRelationRepository salaryVoucherRelationRepository, SystemAccountMapExtendedRepository systemAccountMapExtendedRepository) {
        super(journalVoucherRepository, journalVoucherMapper, journalVoucherSearchRepository);
        this.journalVoucherGeneratorRepository = journalVoucherGeneratorRepository;
        this.dtTransactionQueryService = dtTransactionQueryService;
        this.dtTransactionMapper = dtTransactionMapper;
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionService = dtTransactionService;
        this.currencyExtendedRepository = currencyExtendedRepository;
        this.salaryVoucherRelationRepository = salaryVoucherRelationRepository;
        this.systemAccountMapExtendedRepository = systemAccountMapExtendedRepository;
    }

    @Override
    public JournalVoucherDTO save(JournalVoucherDTO journalVoucherDTO) {
        if(journalVoucherDTO.getVoucherNo()==null){
            JournalVoucherGenerator journalVoucherGenerator = new JournalVoucherGenerator();
            journalVoucherGeneratorRepository.save(journalVoucherGenerator);
            String voucherNo = String.format("%06d", journalVoucherGenerator.getId());
            LocalDate currentDate = LocalDate.now();
            journalVoucherDTO.setVoucherNo(currentDate.getYear()+ "JN"+voucherNo);
        }
        journalVoucherDTO.setPostDate(journalVoucherDTO.getPostDate()!=null? LocalDate.now(): journalVoucherDTO.getPostDate());
        updateTransactions(journalVoucherDTO);
        journalVoucherDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        journalVoucherDTO.setModifiedOn(LocalDate.now());
        return super.save(journalVoucherDTO);
    }

    public void updateTransactions(JournalVoucherDTO journalVoucherDTO){
        DtTransactionCriteria criteria = new DtTransactionCriteria();
        StringFilter voucherFilter = new StringFilter();
        voucherFilter.setContains(journalVoucherDTO.getVoucherNo());
        criteria.setVoucherNo(voucherFilter);

        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setEquals(journalVoucherDTO.getVoucherDate());
        criteria.setVoucherDate(localDateFilter);

        List<DtTransactionDTO> transactionDTOS =  dtTransactionQueryService.findByCriteria(criteria);
        transactionDTOS.forEach(t->{
            t.setCurrencyId(journalVoucherDTO.getCurrencyId());
            t.setConvFactor(journalVoucherDTO.getConversionFactor());
            t.setPostDate(journalVoucherDTO.getPostDate());
            t.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
            t.setModifiedOn(LocalDate.now());
        });

        List<DtTransaction> dtTransactions = dtTransactionMapper.toEntity(transactionDTOS);
        dtTransactions = dtTransactionRepository.saveAll(dtTransactions);

        if(journalVoucherDTO.getPostDate()!=null){
            dtTransactionService.updateAccountBalance(dtTransactionMapper.toDto(dtTransactions));
        }
    }

    public void createPayrollJournalEntry(List<MonthlySalary> monthlySalaries, SalaryVoucherRelation salaryVoucherRelation){
        JournalVoucherDTO journalVoucher = new JournalVoucherDTO();
        Currency baseCurrency = currencyExtendedRepository.findByFlag(CurrencyFlag.BASE);
        journalVoucher.setCurrencyId(baseCurrency.getId());
        journalVoucher.setConversionFactor(BigDecimal.ONE);
        journalVoucher.setReference(VoucherReferenceType.PAYROLL);
        journalVoucher.setVoucherDate(LocalDate.now());
        journalVoucher.setModifiedOn(LocalDate.now());
        journalVoucher = save(journalVoucher);

        salaryVoucherRelation.setVoucherNo(journalVoucher.getVoucherNo());
        salaryVoucherRelation.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        salaryVoucherRelation.setModifiedOn(LocalDate.now());
        salaryVoucherRelationRepository.save(salaryVoucherRelation);

        BigDecimal totalAmount = monthlySalaries
            .stream()
            .map(s->s.getGross())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<DtTransaction> voucherTransactions = new ArrayList<>();
        DtTransaction salaryAndAllowanceTransaction = new DtTransaction();
        SystemAccountMap salaryAndAllowanceAccount = systemAccountMapExtendedRepository.findByAccountType(AccountType.SALARY_ALLOWANCES);
        salaryAndAllowanceTransaction.setAccount(salaryAndAllowanceAccount.getAccount());
        salaryAndAllowanceTransaction.setCurrency(baseCurrency);
        salaryAndAllowanceTransaction.setAmount(totalAmount);
        salaryAndAllowanceTransaction.setBalanceType(BalanceType.DEBIT);
        salaryAndAllowanceTransaction.setConvFactor(BigDecimal.ONE);
        salaryAndAllowanceTransaction.setfCurrency(BigDecimal.ONE);
        salaryAndAllowanceTransaction.setNarration("Monthly Salary Journal Voucher");
        salaryAndAllowanceTransaction.setVoucherDate(journalVoucher.getVoucherDate());
        salaryAndAllowanceTransaction.setVoucherNo(journalVoucher.getVoucherNo());
        salaryAndAllowanceTransaction.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        salaryAndAllowanceTransaction.setModifiedOn(LocalDate.now());
        voucherTransactions.add(salaryAndAllowanceTransaction);

        DtTransaction salaryPayableTransaction = new DtTransaction();
        SystemAccountMap salaryPayableAccount = systemAccountMapExtendedRepository.findByAccountType(AccountType.SALARY_PAYABLE);
        salaryPayableTransaction.setAccount(salaryPayableAccount.getAccount());
        salaryPayableTransaction.setCurrency(baseCurrency);
        salaryPayableTransaction.setAmount(totalAmount);
        salaryPayableTransaction.setBalanceType(BalanceType.CREDIT);
        salaryPayableTransaction.setConvFactor(BigDecimal.ONE);
        salaryPayableTransaction.setfCurrency(BigDecimal.ONE);
        salaryPayableTransaction.setNarration("Monthly Salary Journal Voucher");
        salaryPayableTransaction.setVoucherDate(journalVoucher.getVoucherDate());
        salaryPayableTransaction.setVoucherNo(journalVoucher.getVoucherNo());
        salaryPayableTransaction.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        salaryPayableTransaction.setModifiedOn(LocalDate.now());
        voucherTransactions.add(salaryPayableTransaction);

        dtTransactionRepository.saveAll(voucherTransactions);

        journalVoucher.setPostDate(LocalDate.now());
        save(journalVoucher);
    }
}
