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

import org.soptorshi.domain.CreditorLedger;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.CreditorLedgerRepository;
import org.soptorshi.repository.search.CreditorLedgerSearchRepository;
import org.soptorshi.service.dto.CreditorLedgerCriteria;
import org.soptorshi.service.dto.CreditorLedgerDTO;
import org.soptorshi.service.mapper.CreditorLedgerMapper;

/**
 * Service for executing complex queries for CreditorLedger entities in the database.
 * The main input is a {@link CreditorLedgerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CreditorLedgerDTO} or a {@link Page} of {@link CreditorLedgerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CreditorLedgerQueryService extends QueryService<CreditorLedger> {

    private final Logger log = LoggerFactory.getLogger(CreditorLedgerQueryService.class);

    private final CreditorLedgerRepository creditorLedgerRepository;

    private final CreditorLedgerMapper creditorLedgerMapper;

    private final CreditorLedgerSearchRepository creditorLedgerSearchRepository;

    public CreditorLedgerQueryService(CreditorLedgerRepository creditorLedgerRepository, CreditorLedgerMapper creditorLedgerMapper, CreditorLedgerSearchRepository creditorLedgerSearchRepository) {
        this.creditorLedgerRepository = creditorLedgerRepository;
        this.creditorLedgerMapper = creditorLedgerMapper;
        this.creditorLedgerSearchRepository = creditorLedgerSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CreditorLedgerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CreditorLedgerDTO> findByCriteria(CreditorLedgerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CreditorLedger> specification = createSpecification(criteria);
        return creditorLedgerMapper.toDto(creditorLedgerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CreditorLedgerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CreditorLedgerDTO> findByCriteria(CreditorLedgerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CreditorLedger> specification = createSpecification(criteria);
        return creditorLedgerRepository.findAll(specification, page)
            .map(creditorLedgerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CreditorLedgerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CreditorLedger> specification = createSpecification(criteria);
        return creditorLedgerRepository.count(specification);
    }

    /**
     * Function to convert CreditorLedgerCriteria to a {@link Specification}
     */
    private Specification<CreditorLedger> createSpecification(CreditorLedgerCriteria criteria) {
        Specification<CreditorLedger> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CreditorLedger_.id));
            }
            if (criteria.getSerialNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSerialNo(), CreditorLedger_.serialNo));
            }
            if (criteria.getBillNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillNo(), CreditorLedger_.billNo));
            }
            if (criteria.getBillDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillDate(), CreditorLedger_.billDate));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), CreditorLedger_.amount));
            }
            if (criteria.getPaidAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaidAmount(), CreditorLedger_.paidAmount));
            }
            if (criteria.getBalanceType() != null) {
                specification = specification.and(buildSpecification(criteria.getBalanceType(), CreditorLedger_.balanceType));
            }
            if (criteria.getBillClosingFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getBillClosingFlag(), CreditorLedger_.billClosingFlag));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), CreditorLedger_.dueDate));
            }
            if (criteria.getVatNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVatNo(), CreditorLedger_.vatNo));
            }
            if (criteria.getContCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContCode(), CreditorLedger_.contCode));
            }
            if (criteria.getOrderNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderNo(), CreditorLedger_.orderNo));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), CreditorLedger_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), CreditorLedger_.modifiedOn));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(CreditorLedger_.vendor, JoinType.LEFT).get(Vendor_.id)));
            }
        }
        return specification;
    }
}
