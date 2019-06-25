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

import org.soptorshi.domain.Tax;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.TaxRepository;
import org.soptorshi.repository.search.TaxSearchRepository;
import org.soptorshi.service.dto.TaxCriteria;
import org.soptorshi.service.dto.TaxDTO;
import org.soptorshi.service.mapper.TaxMapper;

/**
 * Service for executing complex queries for Tax entities in the database.
 * The main input is a {@link TaxCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaxDTO} or a {@link Page} of {@link TaxDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaxQueryService extends QueryService<Tax> {

    private final Logger log = LoggerFactory.getLogger(TaxQueryService.class);

    private final TaxRepository taxRepository;

    private final TaxMapper taxMapper;

    private final TaxSearchRepository taxSearchRepository;

    public TaxQueryService(TaxRepository taxRepository, TaxMapper taxMapper, TaxSearchRepository taxSearchRepository) {
        this.taxRepository = taxRepository;
        this.taxMapper = taxMapper;
        this.taxSearchRepository = taxSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TaxDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaxDTO> findByCriteria(TaxCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tax> specification = createSpecification(criteria);
        return taxMapper.toDto(taxRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TaxDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxDTO> findByCriteria(TaxCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tax> specification = createSpecification(criteria);
        return taxRepository.findAll(specification, page)
            .map(taxMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaxCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tax> specification = createSpecification(criteria);
        return taxRepository.count(specification);
    }

    /**
     * Function to convert TaxCriteria to a {@link Specification}
     */
    private Specification<Tax> createSpecification(TaxCriteria criteria) {
        Specification<Tax> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Tax_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Tax_.amount));
            }
            if (criteria.getTaxStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxStatus(), Tax_.taxStatus));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Tax_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), Tax_.modifiedOn));
            }
            if (criteria.getFinancialAccountYearId() != null) {
                specification = specification.and(buildSpecification(criteria.getFinancialAccountYearId(),
                    root -> root.join(Tax_.financialAccountYear, JoinType.LEFT).get(FinancialAccountYear_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Tax_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
