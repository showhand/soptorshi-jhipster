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

import org.soptorshi.domain.WorkOrder;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.WorkOrderRepository;
import org.soptorshi.repository.search.WorkOrderSearchRepository;
import org.soptorshi.service.dto.WorkOrderCriteria;
import org.soptorshi.service.dto.WorkOrderDTO;
import org.soptorshi.service.mapper.WorkOrderMapper;

/**
 * Service for executing complex queries for WorkOrder entities in the database.
 * The main input is a {@link WorkOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkOrderDTO} or a {@link Page} of {@link WorkOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkOrderQueryService extends QueryService<WorkOrder> {

    private final Logger log = LoggerFactory.getLogger(WorkOrderQueryService.class);

    private final WorkOrderRepository workOrderRepository;

    private final WorkOrderMapper workOrderMapper;

    private final WorkOrderSearchRepository workOrderSearchRepository;

    public WorkOrderQueryService(WorkOrderRepository workOrderRepository, WorkOrderMapper workOrderMapper, WorkOrderSearchRepository workOrderSearchRepository) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderMapper = workOrderMapper;
        this.workOrderSearchRepository = workOrderSearchRepository;
    }

    /**
     * Return a {@link List} of {@link WorkOrderDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkOrderDTO> findByCriteria(WorkOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkOrder> specification = createSpecification(criteria);
        return workOrderMapper.toDto(workOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WorkOrderDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkOrderDTO> findByCriteria(WorkOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkOrder> specification = createSpecification(criteria);
        return workOrderRepository.findAll(specification, page)
            .map(workOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkOrder> specification = createSpecification(criteria);
        return workOrderRepository.count(specification);
    }

    /**
     * Function to convert WorkOrderCriteria to a {@link Specification}
     */
    private Specification<WorkOrder> createSpecification(WorkOrderCriteria criteria) {
        Specification<WorkOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), WorkOrder_.id));
            }
            if (criteria.getReferenceNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceNo(), WorkOrder_.referenceNo));
            }
            if (criteria.getIssueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssueDate(), WorkOrder_.issueDate));
            }
            if (criteria.getReferredTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferredTo(), WorkOrder_.referredTo));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), WorkOrder_.subject));
            }
            if (criteria.getLaborOrOtherAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLaborOrOtherAmount(), WorkOrder_.laborOrOtherAmount));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), WorkOrder_.discount));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), WorkOrder_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), WorkOrder_.modifiedOn));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(WorkOrder_.requisition, JoinType.LEFT).get(Requisition_.id)));
            }
        }
        return specification;
    }
}
