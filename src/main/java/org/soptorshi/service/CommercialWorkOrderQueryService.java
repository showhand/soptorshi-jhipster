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

import org.soptorshi.domain.CommercialWorkOrder;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.CommercialWorkOrderRepository;
import org.soptorshi.repository.search.CommercialWorkOrderSearchRepository;
import org.soptorshi.service.dto.CommercialWorkOrderCriteria;
import org.soptorshi.service.dto.CommercialWorkOrderDTO;
import org.soptorshi.service.mapper.CommercialWorkOrderMapper;

/**
 * Service for executing complex queries for CommercialWorkOrder entities in the database.
 * The main input is a {@link CommercialWorkOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialWorkOrderDTO} or a {@link Page} of {@link CommercialWorkOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialWorkOrderQueryService extends QueryService<CommercialWorkOrder> {

    private final Logger log = LoggerFactory.getLogger(CommercialWorkOrderQueryService.class);

    private final CommercialWorkOrderRepository commercialWorkOrderRepository;

    private final CommercialWorkOrderMapper commercialWorkOrderMapper;

    private final CommercialWorkOrderSearchRepository commercialWorkOrderSearchRepository;

    public CommercialWorkOrderQueryService(CommercialWorkOrderRepository commercialWorkOrderRepository, CommercialWorkOrderMapper commercialWorkOrderMapper, CommercialWorkOrderSearchRepository commercialWorkOrderSearchRepository) {
        this.commercialWorkOrderRepository = commercialWorkOrderRepository;
        this.commercialWorkOrderMapper = commercialWorkOrderMapper;
        this.commercialWorkOrderSearchRepository = commercialWorkOrderSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialWorkOrderDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialWorkOrderDTO> findByCriteria(CommercialWorkOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialWorkOrder> specification = createSpecification(criteria);
        return commercialWorkOrderMapper.toDto(commercialWorkOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialWorkOrderDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialWorkOrderDTO> findByCriteria(CommercialWorkOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialWorkOrder> specification = createSpecification(criteria);
        return commercialWorkOrderRepository.findAll(specification, page)
            .map(commercialWorkOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialWorkOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialWorkOrder> specification = createSpecification(criteria);
        return commercialWorkOrderRepository.count(specification);
    }

    /**
     * Function to convert CommercialWorkOrderCriteria to a {@link Specification}
     */
    private Specification<CommercialWorkOrder> createSpecification(CommercialWorkOrderCriteria criteria) {
        Specification<CommercialWorkOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialWorkOrder_.id));
            }
            if (criteria.getRefNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefNo(), CommercialWorkOrder_.refNo));
            }
            if (criteria.getWorkOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWorkOrderDate(), CommercialWorkOrder_.workOrderDate));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), CommercialWorkOrder_.deliveryDate));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), CommercialWorkOrder_.remarks));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialWorkOrder_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), CommercialWorkOrder_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialWorkOrder_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialWorkOrder_.updatedOn));
            }
            if (criteria.getCommercialPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPurchaseOrderId(),
                    root -> root.join(CommercialWorkOrder_.commercialPurchaseOrder, JoinType.LEFT).get(CommercialPurchaseOrder_.id)));
            }
        }
        return specification;
    }
}
