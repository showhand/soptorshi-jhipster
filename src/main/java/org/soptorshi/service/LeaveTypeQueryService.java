package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.LeaveType;
import org.soptorshi.domain.LeaveType_;
import org.soptorshi.repository.LeaveTypeRepository;
import org.soptorshi.repository.search.LeaveTypeSearchRepository;
import org.soptorshi.service.dto.LeaveTypeCriteria;
import org.soptorshi.service.dto.LeaveTypeDTO;
import org.soptorshi.service.mapper.LeaveTypeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for LeaveType entities in the database.
 * The main input is a {@link LeaveTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveTypeDTO} or a {@link Page} of {@link LeaveTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveTypeQueryService extends QueryService<LeaveType> {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeQueryService.class);

    private final LeaveTypeRepository leaveTypeRepository;

    private final LeaveTypeMapper leaveTypeMapper;

    private final LeaveTypeSearchRepository leaveTypeSearchRepository;

    public LeaveTypeQueryService(LeaveTypeRepository leaveTypeRepository, LeaveTypeMapper leaveTypeMapper, LeaveTypeSearchRepository leaveTypeSearchRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
        this.leaveTypeMapper = leaveTypeMapper;
        this.leaveTypeSearchRepository = leaveTypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveTypeDTO> findByCriteria(LeaveTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveType> specification = createSpecification(criteria);
        return leaveTypeMapper.toDto(leaveTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LeaveTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeDTO> findByCriteria(LeaveTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveType> specification = createSpecification(criteria);
        return leaveTypeRepository.findAll(specification, page)
            .map(leaveTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveType> specification = createSpecification(criteria);
        return leaveTypeRepository.count(specification);
    }

    /**
     * Function to convert LeaveTypeCriteria to a {@link Specification}
     */
    private Specification<LeaveType> createSpecification(LeaveTypeCriteria criteria) {
        Specification<LeaveType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LeaveType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LeaveType_.name));
            }
            if (criteria.getPaidLeave() != null) {
                specification = specification.and(buildSpecification(criteria.getPaidLeave(), LeaveType_.paidLeave));
            }
            if (criteria.getMaximumNumberOfDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaximumNumberOfDays(), LeaveType_.maximumNumberOfDays));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), LeaveType_.description));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LeaveType_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), LeaveType_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), LeaveType_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), LeaveType_.updatedOn));
            }
        }
        return specification;
    }
}
