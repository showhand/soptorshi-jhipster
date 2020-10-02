package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.soptorshi.domain.*;
import org.soptorshi.domain.Currency;
import org.soptorshi.domain.enumeration.*;
import org.soptorshi.repository.*;
import org.soptorshi.repository.extended.*;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JournalVoucherExtendedService extends JournalVoucherService {

    private final JournalVoucherGeneratorRepository journalVoucherGeneratorRepository;
    private final DtTransactionQueryService dtTransactionQueryService;
    private final DtTransactionMapper dtTransactionMapper;
    private final DtTransactionRepository dtTransactionRepository;
    private final DtTransactionExtendedService dtTransactionService;
    private final CurrencyExtendedRepository currencyExtendedRepository;
    private final SalaryVoucherRelationRepository salaryVoucherRelationRepository;
    private final SystemAccountMapExtendedRepository systemAccountMapExtendedRepository;
    private final RequisitionVoucherRelationExtendedService requisitionVoucherRelationService;
    private final RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository;
    private final JournalVoucherExtendedRepository journalVoucherExtendedRepository;
    private final JournalVoucherMapper journalVoucherMapper;
    private final PurchaseOrderVoucherRelationExtendedService purchaseOrderVoucherRelationExtendedService;
    private final PurchaseOrderVoucherRelationExtendedRepository purchaseOrderVoucherRelationExtendedRepository;

    public JournalVoucherExtendedService(JournalVoucherRepository journalVoucherRepository, JournalVoucherMapper journalVoucherMapper, JournalVoucherSearchRepository journalVoucherSearchRepository, JournalVoucherGeneratorRepository journalVoucherGeneratorRepository, DtTransactionQueryService dtTransactionQueryService, DtTransactionMapper dtTransactionMapper, DtTransactionRepository dtTransactionRepository, DtTransactionExtendedService dtTransactionService, CurrencyExtendedRepository currencyExtendedRepository, SalaryVoucherRelationRepository salaryVoucherRelationRepository, SystemAccountMapExtendedRepository systemAccountMapExtendedRepository, RequisitionVoucherRelationExtendedService requisitionVoucherRelationService, RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository, JournalVoucherExtendedRepository journalVoucherExtendedRepository, JournalVoucherMapper journalVoucherMapper1, PurchaseOrderVoucherRelationExtendedService purchaseOrderVoucherRelationExtendedService, PurchaseOrderVoucherRelationExtendedRepository purchaseOrderVoucherRelationExtendedRepository) {
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
        this.journalVoucherExtendedRepository = journalVoucherExtendedRepository;
        this.journalVoucherMapper = journalVoucherMapper1;
        this.purchaseOrderVoucherRelationExtendedService = purchaseOrderVoucherRelationExtendedService;
        this.purchaseOrderVoucherRelationExtendedRepository = purchaseOrderVoucherRelationExtendedRepository;
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
        journalVoucherDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        journalVoucherDTO.setModifiedOn(LocalDate.now());
        journalVoucherDTO = super.save(journalVoucherDTO);
        if(journalVoucherDTO.getApplicationType()!=null) {
            storeApplicationVoucherRelation(journalVoucherDTO);
        }
        updateTransactions(journalVoucherDTO);
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
            else if(journalVoucherDTO.getApplicationType().equals(ApplicationType.PURCHASE_ORDER)){
                    purchaseOrderVoucherRelationExtendedService.storePurchaseOrderVoucherRelation(journalVoucherDTO.getVoucherNo(),
                        journalVoucherDTO.getApplicationId(),
                        journalVoucherDTO.getId(),
                        "Journal Voucher");
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
            t.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
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

    public void updateApplicationVoucherRelationAmount(ApplicationType applicationType, String voucherNo, List<DtTransactionDTO> transactionDTOS) {
            BigDecimal totalAmount = transactionDTOS.stream()
                .filter(t->t.getBalanceType().equals(BalanceType.DEBIT))
                .map(DtTransactionDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            if(applicationType.equals(ApplicationType.REQUISITION)){
                RequisitionVoucherRelation requisitionVoucherRelation = requisitionVoucherRelationExtendedRepository.findByVoucherNo(voucherNo);
                requisitionVoucherRelation.setAmount(totalAmount);
                requisitionVoucherRelationExtendedRepository.save(requisitionVoucherRelation);
            }
            else if(applicationType.equals(ApplicationType.PURCHASE_ORDER)){
                if(purchaseOrderVoucherRelationExtendedRepository.existsByVoucherNo(voucherNo)){
                    List<PurchaseOrderVoucherRelation> purchaseOrderVoucherRelations = purchaseOrderVoucherRelationExtendedRepository.findByVoucherNo(voucherNo);
                    PurchaseOrderVoucherRelation purchaseOrderVoucherRelation = purchaseOrderVoucherRelations.get(0);
                    purchaseOrderVoucherRelation.setAmount(totalAmount);
                    purchaseOrderVoucherRelationExtendedRepository.save(purchaseOrderVoucherRelation);
                }
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
            .map(MonthlySalary::getPayable)
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

    public Optional<JournalVoucherDTO> getByVoucherNo(String voucherNo){
        return  journalVoucherExtendedRepository.getByVoucherNo(voucherNo)
            .map(journalVoucherMapper::toDto);
    }
}
