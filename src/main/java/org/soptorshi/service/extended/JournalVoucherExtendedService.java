package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.soptorshi.domain.*;
import org.soptorshi.domain.Currency;
import org.soptorshi.domain.enumeration.*;
import org.soptorshi.repository.*;
import org.soptorshi.repository.extended.CurrencyExtendedRepository;
import org.soptorshi.repository.extended.RequisitionVoucherRelationExtendedRepository;
import org.soptorshi.repository.extended.SystemAccountMapExtendedRepository;
import org.soptorshi.repository.search.JournalVoucherSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.*;
import org.soptorshi.service.dto.DtTransactionCriteria;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.dto.RequisitionVoucherRelationDTO;
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
    private RequisitionVoucherRelationExtendedService requisitionVoucherRelationService;
    private RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository;

    public JournalVoucherExtendedService(JournalVoucherRepository journalVoucherRepository, JournalVoucherMapper journalVoucherMapper, JournalVoucherSearchRepository journalVoucherSearchRepository, JournalVoucherGeneratorRepository journalVoucherGeneratorRepository, DtTransactionQueryService dtTransactionQueryService, DtTransactionMapper dtTransactionMapper, DtTransactionRepository dtTransactionRepository, DtTransactionExtendedService dtTransactionService, CurrencyExtendedRepository currencyExtendedRepository, SalaryVoucherRelationRepository salaryVoucherRelationRepository, SystemAccountMapExtendedRepository systemAccountMapExtendedRepository, RequisitionVoucherRelationExtendedService requisitionVoucherRelationService, RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository) {
        super(journalVoucherRepository, journalVoucherMapper, journalVoucherSearchRepository);
        this.journalVoucherGeneratorRepository = journalVoucherGeneratorRepository;
        this.dtTransactionQueryService = dtTransactionQueryService;
        this.dtTransactionMapper = dtTransactionMapper;
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionService = dtTransactionService;
        this.currencyExtendedRepository = currencyExtendedRepository;
        this.salaryVoucherRelationRepository = salaryVoucherRelationRepository;
        this.systemAccountMapExtendedRepository = systemAccountMapExtendedRepository;
        this.requisitionVoucherRelationService = requisitionVoucherRelationService;
        this.requisitionVoucherRelationExtendedRepository = requisitionVoucherRelationExtendedRepository;
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
        journalVoucherDTO = super.save(journalVoucherDTO);
        if(journalVoucherDTO.getApplicationType()!=null)
            storeApplicationVoucherRelation(journalVoucherDTO);
        return journalVoucherDTO;
    }

    private void storeApplicationVoucherRelation(JournalVoucherDTO journalVoucherDTO) {
        if(!requisitionVoucherRelationExtendedRepository.existsByVoucherNo(journalVoucherDTO.getVoucherNo())){
            if(journalVoucherDTO.getApplicationType().equals(ApplicationType.REQUISITION)){
                requisitionVoucherRelationService.storeRequisitionVoucherRelation(journalVoucherDTO.getVoucherNo(),
                    journalVoucherDTO.getApplicationType(),
                    journalVoucherDTO.getApplicationId(),
                    journalVoucherDTO.getId(),
                    "Journal Voucher");
            }
        }
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

        if(journalVoucherDTO.getApplicationType()!=null)
            updateApplicationVoucherRelationAmount(journalVoucherDTO.getApplicationType(), journalVoucherDTO.getVoucherNo(), transactionDTOS);

        List<DtTransaction> dtTransactions = dtTransactionMapper.toEntity(transactionDTOS);
        dtTransactions = dtTransactionRepository.saveAll(dtTransactions);

        if(journalVoucherDTO.getPostDate()!=null){
            dtTransactionService.updateAccountBalance(dtTransactionMapper.toDto(dtTransactions));
        }
    }

    private void updateApplicationVoucherRelationAmount(ApplicationType applicationType, String voucherNo, List<DtTransactionDTO> transactionDTOS) {
            BigDecimal totalAmount = transactionDTOS.stream()
                .filter(t->t.getBalanceType().equals(BalanceType.DEBIT))
                .map(t->t.getAmount())
                .reduce(BigDecimal.ZERO, (t1,t2)->t1.add(t2));
            if(applicationType.equals(ApplicationType.REQUISITION)){
                RequisitionVoucherRelation requisitionVoucherRelation = requisitionVoucherRelationExtendedRepository.findByVoucherNo(voucherNo);
                requisitionVoucherRelation.setAmount(totalAmount);
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
