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

import org.soptorshi.domain.PeriodClose;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.PeriodCloseRepository;
import org.soptorshi.repository.search.PeriodCloseSearchRepository;
import org.soptorshi.service.dto.PeriodCloseCriteria;
import org.soptorshi.service.dto.PeriodCloseDTO;
import org.soptorshi.service.mapper.PeriodCloseMapper;

/**
 * Service for executing complex queries for PeriodClose entities in the database.
 * The main input is a {@link PeriodCloseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PeriodCloseDTO} or a {@link Page} of {@link PeriodCloseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PeriodCloseQueryService extends QueryService<PeriodClose> {

    private final Logger log = LoggerFactory.getLogger(PeriodCloseQueryService.class);

    protected final PeriodCloseRepository periodCloseRepository;

    protected final PeriodCloseMapper periodCloseMapper;

    protected final PeriodCloseSearchRepository periodCloseSearchRepository;

    public PeriodCloseQueryService(PeriodCloseRepository periodCloseRepository, PeriodCloseMapper periodCloseMapper, PeriodCloseSearchRepository periodCloseSearchRepository) {
        this.periodCloseRepository = periodCloseRepository;
        this.periodCloseMapper = periodCloseMapper;
        this.periodCloseSearchRepository = periodCloseSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PeriodCloseDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PeriodCloseDTO> findByCriteria(PeriodCloseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PeriodClose> specification = createSpecification(criteria);
        return periodCloseMapper.toDto(periodCloseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PeriodCloseDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PeriodCloseDTO> findByCriteria(PeriodCloseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PeriodClose> specification = createSpecification(criteria);
        return periodCloseRepository.findAll(specification, page)
            .map(periodCloseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PeriodCloseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PeriodClose> specification = createSpecification(criteria);
        return periodCloseRepository.count(specification);
    }

    /**
     * Function to convert PeriodCloseCriteria to a {@link Specification}
     */
    protected Specification<PeriodClose> createSpecification(PeriodCloseCriteria criteria) {
        Specification<PeriodClose> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PeriodClose_.id));
            }
            if (criteria.getMonthType() != null) {
                specification = specification.and(buildSpecification(criteria.getMonthType(), PeriodClose_.monthType));
            }
            if (criteria.getCloseYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCloseYear(), PeriodClose_.closeYear));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getFlag(), PeriodClose_.flag));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), PeriodClose_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), PeriodClose_.modifiedOn));
            }
            if (criteria.getFinancialAccountYearId() != null) {
                specification = specification.and(buildSpecification(criteria.getFinancialAccountYearId(),
                    root -> root.join(PeriodClose_.financialAccountYear, JoinType.LEFT).get(FinancialAccountYear_.id)));
            }
        }
        return specification;
    }
}
