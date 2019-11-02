package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.ContraVoucherGenerator;
import org.soptorshi.repository.ContraVoucherGeneratorRepository;
import org.soptorshi.repository.ContraVoucherRepository;
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.search.ContraVoucherSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.ContraVoucherService;
import org.soptorshi.service.DtTransactionQueryService;
import org.soptorshi.service.dto.ContraVoucherDTO;
import org.soptorshi.service.dto.DtTransactionCriteria;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.mapper.ContraVoucherMapper;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ContraVoucherExtendedService extends ContraVoucherService {
    private ContraVoucherGeneratorRepository contraVoucherGeneratorRepository;
    private DtTransactionQueryService dtTransactionQueryService;
    private DtTransactionMapper dtTransactionMapper;
    private DtTransactionRepository dtTransactionRepository;
    private DtTransactionExtendedService dtTransactionExtendedService;

    public ContraVoucherExtendedService(ContraVoucherRepository contraVoucherRepository, ContraVoucherMapper contraVoucherMapper, ContraVoucherSearchRepository contraVoucherSearchRepository, ContraVoucherGeneratorRepository contraVoucherGeneratorRepository, DtTransactionQueryService dtTransactionQueryService, DtTransactionMapper dtTransactionMapper, DtTransactionRepository dtTransactionRepository, DtTransactionExtendedService dtTransactionExtendedService) {
        super(contraVoucherRepository, contraVoucherMapper, contraVoucherSearchRepository);
        this.contraVoucherGeneratorRepository = contraVoucherGeneratorRepository;
        this.dtTransactionQueryService = dtTransactionQueryService;
        this.dtTransactionMapper = dtTransactionMapper;
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionExtendedService = dtTransactionExtendedService;
    }

    @Override
    public ContraVoucherDTO save(ContraVoucherDTO contraVoucherDTO) {
        if(contraVoucherDTO.getVoucherNo()==null){
            ContraVoucherGenerator contraVoucherGenerator = new ContraVoucherGenerator();
            contraVoucherGeneratorRepository.save(contraVoucherGenerator);
            String voucherNo = String.format("%06d", contraVoucherGenerator.getId());
            contraVoucherDTO.setVoucherNo("CV"+voucherNo);
        }
        contraVoucherDTO.setPostDate(contraVoucherDTO.getPostDate()!=null? LocalDate.now(): contraVoucherDTO.getPostDate());
        updateTransactions(contraVoucherDTO);
        contraVoucherDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        contraVoucherDTO.setModifiedOn(LocalDate.now());
        return super.save(contraVoucherDTO);
    }

    public void updateTransactions(ContraVoucherDTO contraVoucherDTO){
        DtTransactionCriteria criteria = new DtTransactionCriteria();
        StringFilter voucherFilter = new StringFilter();
        voucherFilter.setContains(contraVoucherDTO.getVoucherNo());
        criteria.setVoucherNo(voucherFilter);

        LocalDateFilter localDateFilter = new LocalDateFilter();
        localDateFilter.setEquals(contraVoucherDTO.getVoucherDate());
        criteria.setVoucherDate(localDateFilter);

        List<DtTransactionDTO> transactionDTOS =  dtTransactionQueryService.findByCriteria(criteria);
        transactionDTOS.forEach(t->{
            t.setCurrencyId(contraVoucherDTO.getCurrencyId());
            t.setConvFactor(contraVoucherDTO.getConversionFactor());
            t.setPostDate(contraVoucherDTO.getPostDate());
            t.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
            t.setModifiedOn(LocalDate.now());
        });

        List<DtTransaction> dtTransactions = dtTransactionMapper.toEntity(transactionDTOS);
        dtTransactions = dtTransactionRepository.saveAll(dtTransactions);

        if(contraVoucherDTO.getPostDate()!=null){
            dtTransactionExtendedService.updateAccountBalance(dtTransactionMapper.toDto(dtTransactions));
        }
    }
}
