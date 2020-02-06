package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Weekend;
import org.soptorshi.domain.Weekend_;
import org.soptorshi.repository.WeekendRepository;
import org.soptorshi.repository.search.WeekendSearchRepository;
import org.soptorshi.service.dto.WeekendCriteria;
import org.soptorshi.service.dto.WeekendDTO;
import org.soptorshi.service.mapper.WeekendMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for Weekend entities in the database.
 * The main input is a {@link WeekendCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WeekendDTO} or a {@link Page} of {@link WeekendDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WeekendQueryService extends QueryService<Weekend> {

    private final Logger log = LoggerFactory.getLogger(WeekendQueryService.class);

    private final WeekendRepository weekendRepository;

    private final WeekendMapper weekendMapper;

    private final WeekendSearchRepository weekendSearchRepository;

    public WeekendQueryService(WeekendRepository weekendRepository, WeekendMapper weekendMapper, WeekendSearchRepository weekendSearchRepository) {
        this.weekendRepository = weekendRepository;
        this.weekendMapper = weekendMapper;
        this.weekendSearchRepository = weekendSearchRepository;
    }

    /**
     * Return a {@link List} of {@link WeekendDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WeekendDTO> findByCriteria(WeekendCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Weekend> specification = createSpecification(criteria);
        return weekendMapper.toDto(weekendRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WeekendDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WeekendDTO> findByCriteria(WeekendCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Weekend> specification = createSpecification(criteria);
        return weekendRepository.findAll(specification, page)
            .map(weekendMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WeekendCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Weekend> specification = createSpecification(criteria);
        return weekendRepository.count(specification);
    }

    /**
     * Function to convert WeekendCriteria to a {@link Specification}
     */
    private Specification<Weekend> createSpecification(WeekendCriteria criteria) {
        Specification<Weekend> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Weekend_.id));
            }
            if (criteria.getNumberOfDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfDays(), Weekend_.numberOfDays));
            }
            if (criteria.getActiveFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActiveFrom(), Weekend_.activeFrom));
            }
            if (criteria.getActiveTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActiveTo(), Weekend_.activeTo));
            }
            if (criteria.getDay1() != null) {
                specification = specification.and(buildSpecification(criteria.getDay1(), Weekend_.day1));
            }
            if (criteria.getDay2() != null) {
                specification = specification.and(buildSpecification(criteria.getDay2(), Weekend_.day2));
            }
            if (criteria.getDay3() != null) {
                specification = specification.and(buildSpecification(criteria.getDay3(), Weekend_.day3));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Weekend_.status));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Weekend_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Weekend_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), Weekend_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), Weekend_.updatedOn));
            }
        }
        return specification;
    }
}
