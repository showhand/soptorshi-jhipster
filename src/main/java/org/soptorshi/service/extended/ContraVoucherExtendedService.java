package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.ContraVoucherGenerator;
import org.soptorshi.domain.RequisitionVoucherRelation;
import org.soptorshi.domain.enumeration.ApplicationType;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.repository.ContraVoucherGeneratorRepository;
import org.soptorshi.repository.ContraVoucherRepository;
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.extended.ContraVoucherExtendedRepository;
import org.soptorshi.repository.extended.RequisitionExtendedRepository;
import org.soptorshi.repository.extended.RequisitionVoucherRelationExtendedRepository;
import org.soptorshi.repository.search.ContraVoucherSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.ContraVoucherService;
import org.soptorshi.service.DtTransactionQueryService;
import org.soptorshi.service.RequisitionVoucherRelationService;
import org.soptorshi.service.dto.ContraVoucherDTO;
import org.soptorshi.service.dto.DtTransactionCriteria;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.mapper.ContraVoucherMapper;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContraVoucherExtendedService extends ContraVoucherService {
    private ContraVoucherGeneratorRepository contraVoucherGeneratorRepository;
    private DtTransactionQueryService dtTransactionQueryService;
    private DtTransactionMapper dtTransactionMapper;
    private DtTransactionRepository dtTransactionRepository;
    private DtTransactionExtendedService dtTransactionExtendedService;
    private RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository;
    private RequisitionVoucherRelationExtendedService requisitionVoucherRelationService;
    private ContraVoucherExtendedRepository contraVoucherExtendedRepository;
    private ContraVoucherMapper contraVoucherMapper;

    public ContraVoucherExtendedService(ContraVoucherRepository contraVoucherRepository, ContraVoucherMapper contraVoucherMapper, ContraVoucherSearchRepository contraVoucherSearchRepository, ContraVoucherGeneratorRepository contraVoucherGeneratorRepository, DtTransactionQueryService dtTransactionQueryService, DtTransactionMapper dtTransactionMapper, DtTransactionRepository dtTransactionRepository, DtTransactionExtendedService dtTransactionExtendedService, RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository, RequisitionVoucherRelationExtendedService requisitionVoucherRelationService, ContraVoucherExtendedRepository contraVoucherExtendedRepository, ContraVoucherMapper contraVoucherMapper1) {
        super(contraVoucherRepository, contraVoucherMapper, contraVoucherSearchRepository);
        this.contraVoucherGeneratorRepository = contraVoucherGeneratorRepository;
        this.dtTransactionQueryService = dtTransactionQueryService;
        this.dtTransactionMapper = dtTransactionMapper;
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionExtendedService = dtTransactionExtendedService;
        this.requisitionVoucherRelationExtendedRepository = requisitionVoucherRelationExtendedRepository;
        this.requisitionVoucherRelationService = requisitionVoucherRelationService;
        this.contraVoucherExtendedRepository = contraVoucherExtendedRepository;
        this.contraVoucherMapper = contraVoucherMapper1;
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
        contraVoucherDTO = super.save(contraVoucherDTO);
        if(contraVoucherDTO.getApplicationId()!=null)
            storeApplicationVoucherRelation(contraVoucherDTO);
        return contraVoucherDTO;
    }

    private void storeApplicationVoucherRelation(ContraVoucherDTO contraVOucherDTO) {
        if(!requisitionVoucherRelationExtendedRepository.existsByVoucherNo(contraVOucherDTO.getVoucherNo())){
            if(contraVOucherDTO.getApplicationType().equals(ApplicationType.REQUISITION)){
                requisitionVoucherRelationService.storeRequisitionVoucherRelation(contraVOucherDTO.getVoucherNo(),
                    contraVOucherDTO.getApplicationType(),
                    contraVOucherDTO.getApplicationId(),
                    contraVOucherDTO.getId(),
                    "Journal Voucher");
            }
        }
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
        updateApplicationVoucherRelationAmount(contraVoucherDTO.getApplicationType(), contraVoucherDTO.getVoucherNo(), transactionDTOS);
        if(contraVoucherDTO.getPostDate()!=null){
            dtTransactionExtendedService.updateAccountBalance(dtTransactionMapper.toDto(dtTransactions));
        }
    }

    public void updateApplicationVoucherRelationAmount(ApplicationType applicationType, String voucherNo, List<DtTransactionDTO> transactionDTOS) {
        BigDecimal totalAmount = transactionDTOS.stream()
            .filter(t->t.getBalanceType().equals(BalanceType.DEBIT))
            .map(t->t.getAmount())
            .reduce(BigDecimal.ZERO, (t1,t2)->t1.add(t2));
        if(applicationType.equals(ApplicationType.REQUISITION)){
            RequisitionVoucherRelation requisitionVoucherRelation = requisitionVoucherRelationExtendedRepository.findByVoucherNo(voucherNo);
            requisitionVoucherRelation.setAmount(totalAmount);
            requisitionVoucherRelationExtendedRepository.save(requisitionVoucherRelation);
        }
    }

    public Optional<ContraVoucherDTO> findByVoucherNo(String voucherNo){
        return contraVoucherExtendedRepository.findByVoucherNo(voucherNo)
            .map(contraVoucherMapper::toDto);
    }
}
