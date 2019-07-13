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

import org.soptorshi.domain.BudgetAllocation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.BudgetAllocationRepository;
import org.soptorshi.repository.search.BudgetAllocationSearchRepository;
import org.soptorshi.service.dto.BudgetAllocationCriteria;
import org.soptorshi.service.dto.BudgetAllocationDTO;
import org.soptorshi.service.mapper.BudgetAllocationMapper;

/**
 * Service for executing complex queries for BudgetAllocation entities in the database.
 * The main input is a {@link BudgetAllocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BudgetAllocationDTO} or a {@link Page} of {@link BudgetAllocationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BudgetAllocationQueryService extends QueryService<BudgetAllocation> {

    private final Logger log = LoggerFactory.getLogger(BudgetAllocationQueryService.class);

    private final BudgetAllocationRepository budgetAllocationRepository;

    private final BudgetAllocationMapper budgetAllocationMapper;

    private final BudgetAllocationSearchRepository budgetAllocationSearchRepository;

    public BudgetAllocationQueryService(BudgetAllocationRepository budgetAllocationRepository, BudgetAllocationMapper budgetAllocationMapper, BudgetAllocationSearchRepository budgetAllocationSearchRepository) {
        this.budgetAllocationRepository = budgetAllocationRepository;
        this.budgetAllocationMapper = budgetAllocationMapper;
        this.budgetAllocationSearchRepository = budgetAllocationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link BudgetAllocationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BudgetAllocationDTO> findByCriteria(BudgetAllocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BudgetAllocation> specification = createSpecification(criteria);
        return budgetAllocationMapper.toDto(budgetAllocationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BudgetAllocationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BudgetAllocationDTO> findByCriteria(BudgetAllocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BudgetAllocation> specification = createSpecification(criteria);
        return budgetAllocationRepository.findAll(specification, page)
            .map(budgetAllocationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BudgetAllocationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BudgetAllocation> specification = createSpecification(criteria);
        return budgetAllocationRepository.count(specification);
    }

    /**
     * Function to convert BudgetAllocationCriteria to a {@link Specification}
     */
    private Specification<BudgetAllocation> createSpecification(BudgetAllocationCriteria criteria) {
        Specification<BudgetAllocation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BudgetAllocation_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), BudgetAllocation_.amount));
            }
            if (criteria.getOfficeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfficeId(),
                    root -> root.join(BudgetAllocation_.office, JoinType.LEFT).get(Office_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(BudgetAllocation_.department, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getFinancialAccountYearId() != null) {
                specification = specification.and(buildSpecification(criteria.getFinancialAccountYearId(),
                    root -> root.join(BudgetAllocation_.financialAccountYear, JoinType.LEFT).get(FinancialAccountYear_.id)));
            }
        }
        return specification;
    }
}
