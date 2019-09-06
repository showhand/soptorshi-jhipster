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

import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.FinancialAccountYearRepository;
import org.soptorshi.repository.search.FinancialAccountYearSearchRepository;
import org.soptorshi.service.dto.FinancialAccountYearCriteria;
import org.soptorshi.service.dto.FinancialAccountYearDTO;
import org.soptorshi.service.mapper.FinancialAccountYearMapper;

/**
 * Service for executing complex queries for FinancialAccountYear entities in the database.
 * The main input is a {@link FinancialAccountYearCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FinancialAccountYearDTO} or a {@link Page} of {@link FinancialAccountYearDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FinancialAccountYearQueryService extends QueryService<FinancialAccountYear> {

    private final Logger log = LoggerFactory.getLogger(FinancialAccountYearQueryService.class);

    private final FinancialAccountYearRepository financialAccountYearRepository;

    private final FinancialAccountYearMapper financialAccountYearMapper;

    private final FinancialAccountYearSearchRepository financialAccountYearSearchRepository;

    public FinancialAccountYearQueryService(FinancialAccountYearRepository financialAccountYearRepository, FinancialAccountYearMapper financialAccountYearMapper, FinancialAccountYearSearchRepository financialAccountYearSearchRepository) {
        this.financialAccountYearRepository = financialAccountYearRepository;
        this.financialAccountYearMapper = financialAccountYearMapper;
        this.financialAccountYearSearchRepository = financialAccountYearSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FinancialAccountYearDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FinancialAccountYearDTO> findByCriteria(FinancialAccountYearCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FinancialAccountYear> specification = createSpecification(criteria);
        return financialAccountYearMapper.toDto(financialAccountYearRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FinancialAccountYearDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FinancialAccountYearDTO> findByCriteria(FinancialAccountYearCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FinancialAccountYear> specification = createSpecification(criteria);
        return financialAccountYearRepository.findAll(specification, page)
            .map(financialAccountYearMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FinancialAccountYearCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FinancialAccountYear> specification = createSpecification(criteria);
        return financialAccountYearRepository.count(specification);
    }

    /**
     * Function to convert FinancialAccountYearCriteria to a {@link Specification}
     */
    private Specification<FinancialAccountYear> createSpecification(FinancialAccountYearCriteria criteria) {
        Specification<FinancialAccountYear> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FinancialAccountYear_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), FinancialAccountYear_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), FinancialAccountYear_.endDate));
            }
            if (criteria.getPreviousStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreviousStartDate(), FinancialAccountYear_.previousStartDate));
            }
            if (criteria.getPreviousEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreviousEndDate(), FinancialAccountYear_.previousEndDate));
            }
            if (criteria.getDurationStr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDurationStr(), FinancialAccountYear_.durationStr));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), FinancialAccountYear_.status));
            }
        }
        return specification;
    }
}
