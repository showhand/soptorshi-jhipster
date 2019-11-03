package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPurchaseOrderItem;
import org.soptorshi.domain.CommercialPurchaseOrderItem_;
import org.soptorshi.domain.CommercialPurchaseOrder_;
import org.soptorshi.repository.CommercialPurchaseOrderItemRepository;
import org.soptorshi.repository.search.CommercialPurchaseOrderItemSearchRepository;
import org.soptorshi.service.dto.CommercialPurchaseOrderItemCriteria;
import org.soptorshi.service.dto.CommercialPurchaseOrderItemDTO;
import org.soptorshi.service.mapper.CommercialPurchaseOrderItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for CommercialPurchaseOrderItem entities in the database.
 * The main input is a {@link CommercialPurchaseOrderItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialPurchaseOrderItemDTO} or a {@link Page} of {@link CommercialPurchaseOrderItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialPurchaseOrderItemQueryService extends QueryService<CommercialPurchaseOrderItem> {

    private final Logger log = LoggerFactory.getLogger(CommercialPurchaseOrderItemQueryService.class);

    private final CommercialPurchaseOrderItemRepository commercialPurchaseOrderItemRepository;

    private final CommercialPurchaseOrderItemMapper commercialPurchaseOrderItemMapper;

    private final CommercialPurchaseOrderItemSearchRepository commercialPurchaseOrderItemSearchRepository;

    public CommercialPurchaseOrderItemQueryService(CommercialPurchaseOrderItemRepository commercialPurchaseOrderItemRepository, CommercialPurchaseOrderItemMapper commercialPurchaseOrderItemMapper, CommercialPurchaseOrderItemSearchRepository commercialPurchaseOrderItemSearchRepository) {
        this.commercialPurchaseOrderItemRepository = commercialPurchaseOrderItemRepository;
        this.commercialPurchaseOrderItemMapper = commercialPurchaseOrderItemMapper;
        this.commercialPurchaseOrderItemSearchRepository = commercialPurchaseOrderItemSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialPurchaseOrderItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialPurchaseOrderItemDTO> findByCriteria(CommercialPurchaseOrderItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialPurchaseOrderItem> specification = createSpecification(criteria);
        return commercialPurchaseOrderItemMapper.toDto(commercialPurchaseOrderItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialPurchaseOrderItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialPurchaseOrderItemDTO> findByCriteria(CommercialPurchaseOrderItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialPurchaseOrderItem> specification = createSpecification(criteria);
        return commercialPurchaseOrderItemRepository.findAll(specification, page)
            .map(commercialPurchaseOrderItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialPurchaseOrderItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialPurchaseOrderItem> specification = createSpecification(criteria);
        return commercialPurchaseOrderItemRepository.count(specification);
    }

    /**
     * Function to convert CommercialPurchaseOrderItemCriteria to a {@link Specification}
     */
    private Specification<CommercialPurchaseOrderItem> createSpecification(CommercialPurchaseOrderItemCriteria criteria) {
        Specification<CommercialPurchaseOrderItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialPurchaseOrderItem_.id));
            }
            if (criteria.getGoodsOrServices() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoodsOrServices(), CommercialPurchaseOrderItem_.goodsOrServices));
            }
            if (criteria.getDescriptionOfGoodsOrServices() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescriptionOfGoodsOrServices(), CommercialPurchaseOrderItem_.descriptionOfGoodsOrServices));
            }
            if (criteria.getPackaging() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPackaging(), CommercialPurchaseOrderItem_.packaging));
            }
            if (criteria.getSizeOrGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSizeOrGrade(), CommercialPurchaseOrderItem_.sizeOrGrade));
            }
            if (criteria.getQtyOrMc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyOrMc(), CommercialPurchaseOrderItem_.qtyOrMc));
            }
            if (criteria.getQtyOrKgs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyOrKgs(), CommercialPurchaseOrderItem_.qtyOrKgs));
            }
            if (criteria.getRateOrKg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRateOrKg(), CommercialPurchaseOrderItem_.rateOrKg));
            }
            if (criteria.getCurrencyType() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyType(), CommercialPurchaseOrderItem_.currencyType));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), CommercialPurchaseOrderItem_.total));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialPurchaseOrderItem_.createdBy));
            }
            if (criteria.getCreateOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateOn(), CommercialPurchaseOrderItem_.createOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialPurchaseOrderItem_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedOn(), CommercialPurchaseOrderItem_.updatedOn));
            }
            if (criteria.getCommercialPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPurchaseOrderId(),
                    root -> root.join(CommercialPurchaseOrderItem_.commercialPurchaseOrder, JoinType.LEFT).get(CommercialPurchaseOrder_.id)));
            }
        }
        return specification;
    }
}
