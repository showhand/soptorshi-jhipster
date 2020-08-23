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

import org.soptorshi.domain.DepreciationCalculation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.DepreciationCalculationRepository;
import org.soptorshi.repository.search.DepreciationCalculationSearchRepository;
import org.soptorshi.service.dto.DepreciationCalculationCriteria;
import org.soptorshi.service.dto.DepreciationCalculationDTO;
import org.soptorshi.service.mapper.DepreciationCalculationMapper;

/**
 * Service for executing complex queries for DepreciationCalculation entities in the database.
 * The main input is a {@link DepreciationCalculationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DepreciationCalculationDTO} or a {@link Page} of {@link DepreciationCalculationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepreciationCalculationQueryService extends QueryService<DepreciationCalculation> {

    private final Logger log = LoggerFactory.getLogger(DepreciationCalculationQueryService.class);

    private final DepreciationCalculationRepository depreciationCalculationRepository;

    private final DepreciationCalculationMapper depreciationCalculationMapper;

    private final DepreciationCalculationSearchRepository depreciationCalculationSearchRepository;

    public DepreciationCalculationQueryService(DepreciationCalculationRepository depreciationCalculationRepository, DepreciationCalculationMapper depreciationCalculationMapper, DepreciationCalculationSearchRepository depreciationCalculationSearchRepository) {
        this.depreciationCalculationRepository = depreciationCalculationRepository;
        this.depreciationCalculationMapper = depreciationCalculationMapper;
        this.depreciationCalculationSearchRepository = depreciationCalculationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DepreciationCalculationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DepreciationCalculationDTO> findByCriteria(DepreciationCalculationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DepreciationCalculation> specification = createSpecification(criteria);
        return depreciationCalculationMapper.toDto(depreciationCalculationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DepreciationCalculationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepreciationCalculationDTO> findByCriteria(DepreciationCalculationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DepreciationCalculation> specification = createSpecification(criteria);
        return depreciationCalculationRepository.findAll(specification, page)
            .map(depreciationCalculationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepreciationCalculationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DepreciationCalculation> specification = createSpecification(criteria);
        return depreciationCalculationRepository.count(specification);
    }

    /**
     * Function to convert DepreciationCalculationCriteria to a {@link Specification}
     */
    private Specification<DepreciationCalculation> createSpecification(DepreciationCalculationCriteria criteria) {
        Specification<DepreciationCalculation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DepreciationCalculation_.id));
            }
            if (criteria.getMonthType() != null) {
                specification = specification.and(buildSpecification(criteria.getMonthType(), DepreciationCalculation_.monthType));
            }
            if (criteria.getIsExecuted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsExecuted(), DepreciationCalculation_.isExecuted));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), DepreciationCalculation_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), DepreciationCalculation_.createdOn));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), DepreciationCalculation_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), DepreciationCalculation_.modifiedOn));
            }
            if (criteria.getFinancialAccountYearId() != null) {
                specification = specification.and(buildSpecification(criteria.getFinancialAccountYearId(),
                    root -> root.join(DepreciationCalculation_.financialAccountYear, JoinType.LEFT).get(FinancialAccountYear_.id)));
            }
        }
        return specification;
    }
}
