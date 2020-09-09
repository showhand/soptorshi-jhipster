package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.LeaveApplication_;
import org.soptorshi.domain.LeaveAttachment;
import org.soptorshi.domain.LeaveAttachment_;
import org.soptorshi.repository.LeaveAttachmentRepository;
import org.soptorshi.repository.search.LeaveAttachmentSearchRepository;
import org.soptorshi.service.dto.LeaveAttachmentCriteria;
import org.soptorshi.service.dto.LeaveAttachmentDTO;
import org.soptorshi.service.mapper.LeaveAttachmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for LeaveAttachment entities in the database.
 * The main input is a {@link LeaveAttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveAttachmentDTO} or a {@link Page} of {@link LeaveAttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveAttachmentQueryService extends QueryService<LeaveAttachment> {

    private final Logger log = LoggerFactory.getLogger(LeaveAttachmentQueryService.class);

    private final LeaveAttachmentRepository leaveAttachmentRepository;

    private final LeaveAttachmentMapper leaveAttachmentMapper;

    private final LeaveAttachmentSearchRepository leaveAttachmentSearchRepository;

    public LeaveAttachmentQueryService(LeaveAttachmentRepository leaveAttachmentRepository, LeaveAttachmentMapper leaveAttachmentMapper, LeaveAttachmentSearchRepository leaveAttachmentSearchRepository) {
        this.leaveAttachmentRepository = leaveAttachmentRepository;
        this.leaveAttachmentMapper = leaveAttachmentMapper;
        this.leaveAttachmentSearchRepository = leaveAttachmentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveAttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveAttachmentDTO> findByCriteria(LeaveAttachmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveAttachment> specification = createSpecification(criteria);
        return leaveAttachmentMapper.toDto(leaveAttachmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LeaveAttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveAttachmentDTO> findByCriteria(LeaveAttachmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveAttachment> specification = createSpecification(criteria);
        return leaveAttachmentRepository.findAll(specification, page)
            .map(leaveAttachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveAttachmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveAttachment> specification = createSpecification(criteria);
        return leaveAttachmentRepository.count(specification);
    }

    /**
     * Function to convert LeaveAttachmentCriteria to a {@link Specification}
     */
    private Specification<LeaveAttachment> createSpecification(LeaveAttachmentCriteria criteria) {
        Specification<LeaveAttachment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LeaveAttachment_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LeaveAttachment_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), LeaveAttachment_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), LeaveAttachment_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), LeaveAttachment_.updatedOn));
            }
            if (criteria.getLeaveApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getLeaveApplicationId(),
                    root -> root.join(LeaveAttachment_.leaveApplication, JoinType.LEFT).get(LeaveApplication_.id)));
            }
        }
        return specification;
    }
}
