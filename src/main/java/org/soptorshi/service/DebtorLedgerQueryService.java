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

import org.soptorshi.domain.DebtorLedger;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.DebtorLedgerRepository;
import org.soptorshi.repository.search.DebtorLedgerSearchRepository;
import org.soptorshi.service.dto.DebtorLedgerCriteria;
import org.soptorshi.service.dto.DebtorLedgerDTO;
import org.soptorshi.service.mapper.DebtorLedgerMapper;

/**
 * Service for executing complex queries for DebtorLedger entities in the database.
 * The main input is a {@link DebtorLedgerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DebtorLedgerDTO} or a {@link Page} of {@link DebtorLedgerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DebtorLedgerQueryService extends QueryService<DebtorLedger> {

    private final Logger log = LoggerFactory.getLogger(DebtorLedgerQueryService.class);

    private final DebtorLedgerRepository debtorLedgerRepository;

    private final DebtorLedgerMapper debtorLedgerMapper;

    private final DebtorLedgerSearchRepository debtorLedgerSearchRepository;

    public DebtorLedgerQueryService(DebtorLedgerRepository debtorLedgerRepository, DebtorLedgerMapper debtorLedgerMapper, DebtorLedgerSearchRepository debtorLedgerSearchRepository) {
        this.debtorLedgerRepository = debtorLedgerRepository;
        this.debtorLedgerMapper = debtorLedgerMapper;
        this.debtorLedgerSearchRepository = debtorLedgerSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DebtorLedgerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DebtorLedgerDTO> findByCriteria(DebtorLedgerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DebtorLedger> specification = createSpecification(criteria);
        return debtorLedgerMapper.toDto(debtorLedgerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DebtorLedgerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DebtorLedgerDTO> findByCriteria(DebtorLedgerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DebtorLedger> specification = createSpecification(criteria);
        return debtorLedgerRepository.findAll(specification, page)
            .map(debtorLedgerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DebtorLedgerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DebtorLedger> specification = createSpecification(criteria);
        return debtorLedgerRepository.count(specification);
    }

    /**
     * Function to convert DebtorLedgerCriteria to a {@link Specification}
     */
    private Specification<DebtorLedger> createSpecification(DebtorLedgerCriteria criteria) {
        Specification<DebtorLedger> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DebtorLedger_.id));
            }
            if (criteria.getSerialNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNo(), DebtorLedger_.serialNo));
            }
            if (criteria.getBillNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillNo(), DebtorLedger_.billNo));
            }
            if (criteria.getBillDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillDate(), DebtorLedger_.billDate));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), DebtorLedger_.amount));
            }
            if (criteria.getPaidAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaidAmount(), DebtorLedger_.paidAmount));
            }
            if (criteria.getBillClosingFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getBillClosingFlag(), DebtorLedger_.billClosingFlag));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), DebtorLedger_.dueDate));
            }
            if (criteria.getVatNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVatNo(), DebtorLedger_.vatNo));
            }
            if (criteria.getContCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContCode(), DebtorLedger_.contCode));
            }
            if (criteria.getOrderNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderNo(), DebtorLedger_.orderNo));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), DebtorLedger_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), DebtorLedger_.modifiedOn));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(DebtorLedger_.customer, JoinType.LEFT).get(Customer_.id)));
            }
        }
        return specification;
    }
}
