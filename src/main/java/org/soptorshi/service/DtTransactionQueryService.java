package org.soptorshi.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.soptorshi.domain.DtTransaction;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.search.DtTransactionSearchRepository;
import org.soptorshi.service.dto.DtTransactionCriteria;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.mapper.DtTransactionMapper;

/**
 * Service for executing complex queries for DtTransaction entities in the database.
 * The main input is a {@link DtTransactionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DtTransactionDTO} or a {@link Page} of {@link DtTransactionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DtTransactionQueryService extends QueryService<DtTransaction> {

    private final Logger log = LoggerFactory.getLogger(DtTransactionQueryService.class);

    private final DtTransactionRepository dtTransactionRepository;

    private final DtTransactionMapper dtTransactionMapper;

    private final DtTransactionSearchRepository dtTransactionSearchRepository;

    public DtTransactionQueryService(DtTransactionRepository dtTransactionRepository, DtTransactionMapper dtTransactionMapper, DtTransactionSearchRepository dtTransactionSearchRepository) {
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionMapper = dtTransactionMapper;
        this.dtTransactionSearchRepository = dtTransactionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DtTransactionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DtTransactionDTO> findByCriteria(DtTransactionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DtTransaction> specification = createSpecification(criteria);
        return dtTransactionMapper.toDto(dtTransactionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DtTransactionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DtTransactionDTO> findByCriteria(DtTransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DtTransaction> specification = createSpecification(criteria);
        return dtTransactionRepository.findAll(specification, page)
            .map(dtTransactionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DtTransactionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DtTransaction> specification = createSpecification(criteria);
        return dtTransactionRepository.count(specification);
    }

    /**
     * Function to convert DtTransactionCriteria to a {@link Specification}
     */
    private Specification<DtTransaction> createSpecification(DtTransactionCriteria criteria) {
        Specification<DtTransaction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DtTransaction_.id));
            }
            if (criteria.getVoucherNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoucherNo(), DtTransaction_.voucherNo));
            }
            if (criteria.getVoucherDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherDate(), DtTransaction_.voucherDate));
            }
            if (criteria.getSerialNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSerialNo(), DtTransaction_.serialNo));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), DtTransaction_.amount));
            }
            if (criteria.getBalanceType() != null) {
                specification = specification.and(buildSpecification(criteria.getBalanceType(), DtTransaction_.balanceType));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), DtTransaction_.type));
            }
            if (criteria.getInvoiceNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceNo(), DtTransaction_.invoiceNo));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceDate(), DtTransaction_.invoiceDate));
            }
            if (criteria.getInstrumentType() != null) {
                specification = specification.and(buildSpecification(criteria.getInstrumentType(), DtTransaction_.instrumentType));
            }
            if (criteria.getInstrumentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstrumentNo(), DtTransaction_.instrumentNo));
            }
            if (criteria.getInstrumentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstrumentDate(), DtTransaction_.instrumentDate));
            }
            if (criteria.getfCurrency() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getfCurrency(), DtTransaction_.fCurrency));
            }
            if (criteria.getConvFactor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConvFactor(), DtTransaction_.convFactor));
            }
            if (criteria.getPostDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostDate(), DtTransaction_.postDate));
            }
            if (criteria.getNarration() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNarration(), DtTransaction_.narration));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), DtTransaction_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), DtTransaction_.modifiedOn));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), DtTransaction_.reference));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getAccountId(),
                    root -> root.join(DtTransaction_.account, JoinType.LEFT).get(MstAccount_.id)));
            }
            if (criteria.getVoucherId() != null) {
                specification = specification.and(buildSpecification(criteria.getVoucherId(),
                    root -> root.join(DtTransaction_.voucher, JoinType.LEFT).get(Voucher_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(DtTransaction_.currency, JoinType.LEFT).get(Currency_.id)));
            }
        }
        return specification;
    }
}
