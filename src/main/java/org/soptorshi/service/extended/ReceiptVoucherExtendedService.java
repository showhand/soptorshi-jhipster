package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import net.sf.cglib.core.Local;
import org.soptorshi.domain.Currency;
import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.ReceiptVoucherGenerator;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.CurrencyFlag;
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.ReceiptVoucherGeneratorRepository;
import org.soptorshi.repository.ReceiptVoucherRepository;
import org.soptorshi.repository.search.ReceiptVoucherSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CurrencyQueryService;
import org.soptorshi.service.DtTransactionQueryService;
import org.soptorshi.service.ReceiptVoucherService;
import org.soptorshi.service.dto.*;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.soptorshi.service.mapper.ReceiptVoucherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReceiptVoucherExtendedService extends ReceiptVoucherService {
    private ReceiptVoucherGeneratorRepository receiptVoucherGeneratorRepository;
    private DtTransactionQueryService dtTransactionQueryService;
    private DtTransactionRepository dtTransactionRepository;
    private DtTransactionExtendedService dtTransactionExtendedService;
    private CurrencyQueryService currencyQueryService;
    private DtTransactionMapper dtTransactionMapper;

    public ReceiptVoucherExtendedService(ReceiptVoucherRepository receiptVoucherRepository, ReceiptVoucherMapper receiptVoucherMapper, ReceiptVoucherSearchRepository receiptVoucherSearchRepository, ReceiptVoucherGeneratorRepository receiptVoucherGeneratorRepository, DtTransactionQueryService dtTransactionQueryService, DtTransactionRepository dtTransactionRepository, DtTransactionExtendedService dtTransactionExtendedService, CurrencyQueryService currencyQueryService, DtTransactionMapper dtTransactionMapper) {
        super(receiptVoucherRepository, receiptVoucherMapper, receiptVoucherSearchRepository);
        this.receiptVoucherGeneratorRepository = receiptVoucherGeneratorRepository;
        this.dtTransactionQueryService = dtTransactionQueryService;
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionExtendedService = dtTransactionExtendedService;
        this.currencyQueryService = currencyQueryService;
        this.dtTransactionMapper = dtTransactionMapper;
    }

    @Override
    public ReceiptVoucherDTO save(ReceiptVoucherDTO receiptVoucherDTO) {
        if(receiptVoucherDTO.getId()==null){
            ReceiptVoucherGenerator receiptVoucherGenerator = new ReceiptVoucherGenerator();
            receiptVoucherGeneratorRepository.save(receiptVoucherGenerator);
            String voucherNo = String.format("%06d", receiptVoucherGenerator.getId());
            receiptVoucherDTO.setVoucherNo("BR"+voucherNo);
        }
        receiptVoucherDTO.setPostDate(receiptVoucherDTO.getPostDate()==null?receiptVoucherDTO.getPostDate():LocalDate.now());

        return super.save(receiptVoucherDTO);
    }

    public void updateTransactions(ReceiptVoucherDTO receiptVoucherDTO){
        DtTransactionCriteria debitVoucherCriteria = new DtTransactionCriteria();

        StringFilter voucherNoFilter = new StringFilter();
        voucherNoFilter.setEquals(receiptVoucherDTO.getVoucherNo());
        LocalDateFilter voucherDateFilter = new LocalDateFilter();
        voucherDateFilter.setEquals(receiptVoucherDTO.getVoucherDate());
        DtTransactionCriteria.BalanceTypeFilter balanceTypeFilter = new DtTransactionCriteria.BalanceTypeFilter();
        balanceTypeFilter.setEquals(BalanceType.DEBIT);

        debitVoucherCriteria.setVoucherNo(voucherNoFilter);
        debitVoucherCriteria.setVoucherDate(voucherDateFilter);
        debitVoucherCriteria.setBalanceType(balanceTypeFilter);
        List<DtTransactionDTO> debitTransactions = dtTransactionQueryService.findByCriteria(debitVoucherCriteria);

        DtTransactionCriteria.BalanceTypeFilter creditBalanceTypeFilter = new DtTransactionCriteria.BalanceTypeFilter();
        creditBalanceTypeFilter.setEquals(BalanceType.CREDIT);
        DtTransactionCriteria creditVoucherCriteria = new DtTransactionCriteria();
        creditVoucherCriteria.setVoucherNo(voucherNoFilter);
        creditVoucherCriteria.setVoucherDate(voucherDateFilter);
        creditVoucherCriteria.setBalanceType(creditBalanceTypeFilter);
        List<DtTransactionDTO> creditTransactions = dtTransactionQueryService.findByCriteria(creditVoucherCriteria);

        BigDecimal totalAmount = creditTransactions.stream()
            .map(t->t.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(debitTransactions.size()==0)
            debitTransactions.add(createDebitTransaction(receiptVoucherDTO));

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
            t.setConvFactor(BigDecimal.ONE);
            t.setPostDate(receiptVoucherDTO.getPostDate());
            t.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
            t.setModifiedOn(LocalDate.now());
        });

        List<DtTransaction> dtTransactions = dtTransactionMapper.toEntity(combinedTransactions);
        dtTransactions = dtTransactionRepository.saveAll(dtTransactions);

        if(receiptVoucherDTO.getPostDate()!=null){
            dtTransactionExtendedService.updateAccountBalance(dtTransactionMapper.toDto(dtTransactions));
        }
    }

    private DtTransactionDTO createDebitTransaction(ReceiptVoucherDTO receiptVoucherDTO){
        DtTransactionDTO debitTransaction = new DtTransactionDTO();
        debitTransaction.setVoucherNo(receiptVoucherDTO.getVoucherNo());
        debitTransaction.setVoucherDate(receiptVoucherDTO.getVoucherDate());
        debitTransaction.setAmount(BigDecimal.ZERO);
        debitTransaction.setBalanceType(BalanceType.DEBIT);
        debitTransaction.setAccountId(receiptVoucherDTO.getAccountId());
        return debitTransaction;
    }
}
