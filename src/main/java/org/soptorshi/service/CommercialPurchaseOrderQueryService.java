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

import org.soptorshi.domain.CommercialPurchaseOrder;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.CommercialPurchaseOrderRepository;
import org.soptorshi.repository.search.CommercialPurchaseOrderSearchRepository;
import org.soptorshi.service.dto.CommercialPurchaseOrderCriteria;
import org.soptorshi.service.dto.CommercialPurchaseOrderDTO;
import org.soptorshi.service.mapper.CommercialPurchaseOrderMapper;

/**
 * Service for executing complex queries for CommercialPurchaseOrder entities in the database.
 * The main input is a {@link CommercialPurchaseOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialPurchaseOrderDTO} or a {@link Page} of {@link CommercialPurchaseOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialPurchaseOrderQueryService extends QueryService<CommercialPurchaseOrder> {

    private final Logger log = LoggerFactory.getLogger(CommercialPurchaseOrderQueryService.class);

    private final CommercialPurchaseOrderRepository commercialPurchaseOrderRepository;

    private final CommercialPurchaseOrderMapper commercialPurchaseOrderMapper;

    private final CommercialPurchaseOrderSearchRepository commercialPurchaseOrderSearchRepository;

    public CommercialPurchaseOrderQueryService(CommercialPurchaseOrderRepository commercialPurchaseOrderRepository, CommercialPurchaseOrderMapper commercialPurchaseOrderMapper, CommercialPurchaseOrderSearchRepository commercialPurchaseOrderSearchRepository) {
        this.commercialPurchaseOrderRepository = commercialPurchaseOrderRepository;
        this.commercialPurchaseOrderMapper = commercialPurchaseOrderMapper;
        this.commercialPurchaseOrderSearchRepository = commercialPurchaseOrderSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialPurchaseOrderDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialPurchaseOrderDTO> findByCriteria(CommercialPurchaseOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialPurchaseOrder> specification = createSpecification(criteria);
        return commercialPurchaseOrderMapper.toDto(commercialPurchaseOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialPurchaseOrderDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialPurchaseOrderDTO> findByCriteria(CommercialPurchaseOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialPurchaseOrder> specification = createSpecification(criteria);
        return commercialPurchaseOrderRepository.findAll(specification, page)
            .map(commercialPurchaseOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialPurchaseOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialPurchaseOrder> specification = createSpecification(criteria);
        return commercialPurchaseOrderRepository.count(specification);
    }

    /**
     * Function to convert CommercialPurchaseOrderCriteria to a {@link Specification}
     */
    private Specification<CommercialPurchaseOrder> createSpecification(CommercialPurchaseOrderCriteria criteria) {
        Specification<CommercialPurchaseOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialPurchaseOrder_.id));
            }
            if (criteria.getPurchaseOrderNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurchaseOrderNo(), CommercialPurchaseOrder_.purchaseOrderNo));
            }
            if (criteria.getPurchaseOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchaseOrderDate(), CommercialPurchaseOrder_.purchaseOrderDate));
            }
            if (criteria.getOriginOfGoods() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOriginOfGoods(), CommercialPurchaseOrder_.originOfGoods));
            }
            if (criteria.getFinalDestination() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinalDestination(), CommercialPurchaseOrder_.finalDestination));
            }
            if (criteria.getShipmentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShipmentDate(), CommercialPurchaseOrder_.shipmentDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialPurchaseOrder_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), CommercialPurchaseOrder_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialPurchaseOrder_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialPurchaseOrder_.updatedOn));
            }
        }
        return specification;
    }
}
