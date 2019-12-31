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

import org.soptorshi.domain.PurchaseOrder;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.PurchaseOrderRepository;
import org.soptorshi.repository.search.PurchaseOrderSearchRepository;
import org.soptorshi.service.dto.PurchaseOrderCriteria;
import org.soptorshi.service.dto.PurchaseOrderDTO;
import org.soptorshi.service.mapper.PurchaseOrderMapper;

/**
 * Service for executing complex queries for PurchaseOrder entities in the database.
 * The main input is a {@link PurchaseOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseOrderDTO} or a {@link Page} of {@link PurchaseOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseOrderQueryService extends QueryService<PurchaseOrder> {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderQueryService.class);

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final PurchaseOrderMapper purchaseOrderMapper;

    private final PurchaseOrderSearchRepository purchaseOrderSearchRepository;

    public PurchaseOrderQueryService(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderMapper purchaseOrderMapper, PurchaseOrderSearchRepository purchaseOrderSearchRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.purchaseOrderSearchRepository = purchaseOrderSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PurchaseOrderDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseOrderDTO> findByCriteria(PurchaseOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseOrder> specification = createSpecification(criteria);
        return purchaseOrderMapper.toDto(purchaseOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurchaseOrderDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderDTO> findByCriteria(PurchaseOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseOrder> specification = createSpecification(criteria);
        return purchaseOrderRepository.findAll(specification, page)
            .map(purchaseOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseOrder> specification = createSpecification(criteria);
        return purchaseOrderRepository.count(specification);
    }

    /**
     * Function to convert PurchaseOrderCriteria to a {@link Specification}
     */
    private Specification<PurchaseOrder> createSpecification(PurchaseOrderCriteria criteria) {
        Specification<PurchaseOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PurchaseOrder_.id));
            }
            if (criteria.getPurchaseOrderNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurchaseOrderNo(), PurchaseOrder_.purchaseOrderNo));
            }
            if (criteria.getWorkOrderNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkOrderNo(), PurchaseOrder_.workOrderNo));
            }
            if (criteria.getIssueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssueDate(), PurchaseOrder_.issueDate));
            }
            if (criteria.getReferredTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferredTo(), PurchaseOrder_.referredTo));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), PurchaseOrder_.subject));
            }
            if (criteria.getLaborOrOtherAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLaborOrOtherAmount(), PurchaseOrder_.laborOrOtherAmount));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), PurchaseOrder_.discount));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), PurchaseOrder_.status));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), PurchaseOrder_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), PurchaseOrder_.modifiedOn));
            }
            if (criteria.getCommentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommentsId(),
                    root -> root.join(PurchaseOrder_.comments, JoinType.LEFT).get(PurchaseOrderMessages_.id)));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(PurchaseOrder_.requisition, JoinType.LEFT).get(Requisition_.id)));
            }
            if (criteria.getQuotationId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuotationId(),
                    root -> root.join(PurchaseOrder_.quotation, JoinType.LEFT).get(Quotation_.id)));
            }
        }
        return specification;
    }
}
