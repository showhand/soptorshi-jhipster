package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee_;
import org.soptorshi.domain.OverTime;
import org.soptorshi.domain.OverTime_;
import org.soptorshi.repository.OverTimeRepository;
import org.soptorshi.repository.search.OverTimeSearchRepository;
import org.soptorshi.service.dto.OverTimeCriteria;
import org.soptorshi.service.dto.OverTimeDTO;
import org.soptorshi.service.mapper.OverTimeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for OverTime entities in the database.
 * The main input is a {@link OverTimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OverTimeDTO} or a {@link Page} of {@link OverTimeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OverTimeQueryService extends QueryService<OverTime> {

    private final Logger log = LoggerFactory.getLogger(OverTimeQueryService.class);

    private final OverTimeRepository overTimeRepository;

    private final OverTimeMapper overTimeMapper;

    private final OverTimeSearchRepository overTimeSearchRepository;

    public OverTimeQueryService(OverTimeRepository overTimeRepository, OverTimeMapper overTimeMapper, OverTimeSearchRepository overTimeSearchRepository) {
        this.overTimeRepository = overTimeRepository;
        this.overTimeMapper = overTimeMapper;
        this.overTimeSearchRepository = overTimeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link OverTimeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OverTimeDTO> findByCriteria(OverTimeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OverTime> specification = createSpecification(criteria);
        return overTimeMapper.toDto(overTimeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OverTimeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OverTimeDTO> findByCriteria(OverTimeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OverTime> specification = createSpecification(criteria);
        return overTimeRepository.findAll(specification, page)
            .map(overTimeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OverTimeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OverTime> specification = createSpecification(criteria);
        return overTimeRepository.count(specification);
    }

    /**
     * Function to convert OverTimeCriteria to a {@link Specification}
     */
    private Specification<OverTime> createSpecification(OverTimeCriteria criteria) {
        Specification<OverTime> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), OverTime_.id));
            }
            if (criteria.getOverTimeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOverTimeDate(), OverTime_.overTimeDate));
            }
            if (criteria.getFromTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromTime(), OverTime_.fromTime));
            }
            if (criteria.getToTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToTime(), OverTime_.toTime));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuration(), OverTime_.duration));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OverTime_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), OverTime_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), OverTime_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), OverTime_.updatedOn));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(OverTime_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
