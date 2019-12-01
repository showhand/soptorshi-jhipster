package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.CurrencyFlag;
import org.soptorshi.domain.enumeration.VoucherReferenceType;
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.JournalVoucherGeneratorRepository;
import org.soptorshi.repository.JournalVoucherRepository;
import org.soptorshi.repository.extended.CurrencyExtendedRepository;
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

    public JournalVoucherExtendedService(JournalVoucherRepository journalVoucherRepository, JournalVoucherMapper journalVoucherMapper, JournalVoucherSearchRepository journalVoucherSearchRepository, JournalVoucherGeneratorRepository journalVoucherGeneratorRepository, DtTransactionQueryService dtTransactionQueryService, DtTransactionMapper dtTransactionMapper, DtTransactionRepository dtTransactionRepository, DtTransactionExtendedService dtTransactionService, CurrencyExtendedRepository currencyExtendedRepository) {
        super(journalVoucherRepository, journalVoucherMapper, journalVoucherSearchRepository);
        this.journalVoucherGeneratorRepository = journalVoucherGeneratorRepository;
        this.dtTransactionQueryService = dtTransactionQueryService;
        this.dtTransactionMapper = dtTransactionMapper;
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionService = dtTransactionService;
        this.currencyExtendedRepository = currencyExtendedRepository;
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
        journalVoucher.setReferenceId(salaryVoucherRelation.getId());
        journalVoucher.setVoucherDate(LocalDate.now());
        journalVoucher.setModifiedOn(LocalDate.now());
        journalVoucher = save(journalVoucher);
        
    }
}
