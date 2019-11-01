package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.PaymentVoucherGenerator;
import org.soptorshi.domain.ReceiptVoucherGenerator;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.CurrencyFlag;
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.PaymentVoucherGeneratorRepository;
import org.soptorshi.repository.PaymentVoucherRepository;
import org.soptorshi.repository.search.PaymentVoucherSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CurrencyQueryService;
import org.soptorshi.service.DtTransactionQueryService;
import org.soptorshi.service.PaymentVoucherService;
import org.soptorshi.service.dto.*;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.soptorshi.service.mapper.PaymentVoucherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PaymentVoucherExtendedService extends PaymentVoucherService {

    private PaymentVoucherGeneratorRepository paymentVoucherGeneratorRepository;
    private DtTransactionQueryService dtTransactionQueryService;
    private DtTransactionRepository dtTransactionRepository;
    private DtTransactionExtendedService dtTransactionExtendedService;
    private CurrencyQueryService currencyQueryService;
    private DtTransactionMapper dtTransactionMapper;

    public PaymentVoucherExtendedService(PaymentVoucherRepository paymentVoucherRepository, PaymentVoucherMapper paymentVoucherMapper, PaymentVoucherSearchRepository paymentVoucherSearchRepository, PaymentVoucherGeneratorRepository paymentVoucherGeneratorRepository, DtTransactionQueryService dtTransactionQueryService, DtTransactionRepository dtTransactionRepository, DtTransactionExtendedService dtTransactionExtendedService, CurrencyQueryService currencyQueryService, DtTransactionMapper dtTransactionMapper) {
        super(paymentVoucherRepository, paymentVoucherMapper, paymentVoucherSearchRepository);
        this.paymentVoucherGeneratorRepository = paymentVoucherGeneratorRepository;
        this.dtTransactionQueryService = dtTransactionQueryService;
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionExtendedService = dtTransactionExtendedService;
        this.currencyQueryService = currencyQueryService;
        this.dtTransactionMapper = dtTransactionMapper;
    }

    @Override
    public PaymentVoucherDTO save(PaymentVoucherDTO paymentVoucherDTO) {
        if(paymentVoucherDTO.getId()==null){
            PaymentVoucherGenerator paymentVoucherGenerator = new PaymentVoucherGenerator();
            paymentVoucherGeneratorRepository.save(paymentVoucherGenerator);
            String voucherNo = String.format("%06d", paymentVoucherGenerator.getId());
            paymentVoucherDTO.setVoucherNo("BP"+voucherNo);
        }
        paymentVoucherDTO.setPostDate(paymentVoucherDTO.getPostDate()==null?paymentVoucherDTO.getPostDate():LocalDate.now());
        paymentVoucherDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        paymentVoucherDTO.setModifiedOn(LocalDate.now());
        updateTransactions(paymentVoucherDTO);
        return super.save(paymentVoucherDTO);
    }


    public void updateTransactions(PaymentVoucherDTO paymentVoucherDTO){
        DtTransactionCriteria creditVoucherCriteria = new DtTransactionCriteria();

        StringFilter voucherNoFilter = new StringFilter();
        voucherNoFilter.setEquals(paymentVoucherDTO.getVoucherNo());
        LocalDateFilter voucherDateFilter = new LocalDateFilter();
        voucherDateFilter.setEquals(paymentVoucherDTO.getVoucherDate());
        DtTransactionCriteria.BalanceTypeFilter balanceTypeFilter = new DtTransactionCriteria.BalanceTypeFilter();
        balanceTypeFilter.setEquals(BalanceType.CREDIT);

        creditVoucherCriteria.setVoucherNo(voucherNoFilter);
        creditVoucherCriteria.setVoucherDate(voucherDateFilter);
        creditVoucherCriteria.setBalanceType(balanceTypeFilter);
        List<DtTransactionDTO> debitTransactions = dtTransactionQueryService.findByCriteria(creditVoucherCriteria);

        DtTransactionCriteria.BalanceTypeFilter creditBalanceTypeFilter = new DtTransactionCriteria.BalanceTypeFilter();
        creditBalanceTypeFilter.setEquals(BalanceType.DEBIT);
        DtTransactionCriteria debitVoucherCriteria = new DtTransactionCriteria();
        debitVoucherCriteria.setVoucherNo(voucherNoFilter);
        debitVoucherCriteria.setVoucherDate(voucherDateFilter);
        debitVoucherCriteria.setBalanceType(creditBalanceTypeFilter);
        List<DtTransactionDTO> creditTransactions = dtTransactionQueryService.findByCriteria(debitVoucherCriteria);

        BigDecimal totalAmount = creditTransactions.stream()
            .map(t->t.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(debitTransactions.size()==0)
            debitTransactions.add(createCreditTransaction(paymentVoucherDTO));

        debitTransactions.forEach(t->{
            t.setAmount(totalAmount);
        });

        List<DtTransactionDTO> combinedTransactions = new ArrayList<>();
        combinedTransactions.addAll(debitTransactions);
        combinedTransactions.addAll(creditTransactions);

        CurrencyCriteria currencyCriteria = new CurrencyCriteria();
        CurrencyCriteria.CurrencyFlagFilter currencyFlagFilter = new CurrencyCriteria.CurrencyFlagFilter();
        currencyCriteria.equals(CurrencyFlag.BASE);
        currencyCriteria.setFlag(currencyFlagFilter);
        CurrencyDTO baseCurrency = currencyQueryService.findByCriteria(currencyCriteria).get(0);

        combinedTransactions.forEach(t->{
            t.setCurrencyId(baseCurrency.getId());
            t.setfCurrency(BigDecimal.ONE);
            t.setConvFactor(BigDecimal.ONE);
            t.setPostDate(paymentVoucherDTO.getPostDate());
            t.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
            t.setModifiedOn(LocalDate.now());
        });

        List<DtTransaction> dtTransactions = dtTransactionMapper.toEntity(combinedTransactions);
        dtTransactions = dtTransactionRepository.saveAll(dtTransactions);

        if(paymentVoucherDTO.getPostDate()!=null){
            dtTransactionExtendedService.updateAccountBalance(dtTransactionMapper.toDto(dtTransactions));
        }
    }

    private DtTransactionDTO createCreditTransaction(PaymentVoucherDTO paymentVoucherDTO){
        DtTransactionDTO debitTransaction = new DtTransactionDTO();
        debitTransaction.setVoucherNo(paymentVoucherDTO.getVoucherNo());
        debitTransaction.setVoucherDate(paymentVoucherDTO.getVoucherDate());
        debitTransaction.setAmount(BigDecimal.ZERO);
        debitTransaction.setBalanceType(BalanceType.CREDIT);
        debitTransaction.setAccountId(paymentVoucherDTO.getAccountId());
        return debitTransaction;
    }

}
