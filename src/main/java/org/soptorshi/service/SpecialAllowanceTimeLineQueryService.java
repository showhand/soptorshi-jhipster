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

import org.soptorshi.domain.SpecialAllowanceTimeLine;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.SpecialAllowanceTimeLineRepository;
import org.soptorshi.repository.search.SpecialAllowanceTimeLineSearchRepository;
import org.soptorshi.service.dto.SpecialAllowanceTimeLineCriteria;
import org.soptorshi.service.dto.SpecialAllowanceTimeLineDTO;
import org.soptorshi.service.mapper.SpecialAllowanceTimeLineMapper;

/**
 * Service for executing complex queries for SpecialAllowanceTimeLine entities in the database.
 * The main input is a {@link SpecialAllowanceTimeLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SpecialAllowanceTimeLineDTO} or a {@link Page} of {@link SpecialAllowanceTimeLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpecialAllowanceTimeLineQueryService extends QueryService<SpecialAllowanceTimeLine> {

    private final Logger log = LoggerFactory.getLogger(SpecialAllowanceTimeLineQueryService.class);

    private final SpecialAllowanceTimeLineRepository specialAllowanceTimeLineRepository;

    private final SpecialAllowanceTimeLineMapper specialAllowanceTimeLineMapper;

    private final SpecialAllowanceTimeLineSearchRepository specialAllowanceTimeLineSearchRepository;

    public SpecialAllowanceTimeLineQueryService(SpecialAllowanceTimeLineRepository specialAllowanceTimeLineRepository, SpecialAllowanceTimeLineMapper specialAllowanceTimeLineMapper, SpecialAllowanceTimeLineSearchRepository specialAllowanceTimeLineSearchRepository) {
        this.specialAllowanceTimeLineRepository = specialAllowanceTimeLineRepository;
        this.specialAllowanceTimeLineMapper = specialAllowanceTimeLineMapper;
        this.specialAllowanceTimeLineSearchRepository = specialAllowanceTimeLineSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SpecialAllowanceTimeLineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SpecialAllowanceTimeLineDTO> findByCriteria(SpecialAllowanceTimeLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SpecialAllowanceTimeLine> specification = createSpecification(criteria);
        return specialAllowanceTimeLineMapper.toDto(specialAllowanceTimeLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SpecialAllowanceTimeLineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SpecialAllowanceTimeLineDTO> findByCriteria(SpecialAllowanceTimeLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SpecialAllowanceTimeLine> specification = createSpecification(criteria);
        return specialAllowanceTimeLineRepository.findAll(specification, page)
            .map(specialAllowanceTimeLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpecialAllowanceTimeLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SpecialAllowanceTimeLine> specification = createSpecification(criteria);
        return specialAllowanceTimeLineRepository.count(specification);
    }

    /**
     * Function to convert SpecialAllowanceTimeLineCriteria to a {@link Specification}
     */
    private Specification<SpecialAllowanceTimeLine> createSpecification(SpecialAllowanceTimeLineCriteria criteria) {
        Specification<SpecialAllowanceTimeLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SpecialAllowanceTimeLine_.id));
            }
            if (criteria.getAllowanceType() != null) {
                specification = specification.and(buildSpecification(criteria.getAllowanceType(), SpecialAllowanceTimeLine_.allowanceType));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildSpecification(criteria.getReligion(), SpecialAllowanceTimeLine_.religion));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), SpecialAllowanceTimeLine_.year));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), SpecialAllowanceTimeLine_.month));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), SpecialAllowanceTimeLine_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), SpecialAllowanceTimeLine_.modifiedOn));
            }
        }
        return specification;
    }
}
