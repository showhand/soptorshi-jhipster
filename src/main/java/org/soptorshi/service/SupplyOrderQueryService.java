package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyOrderRepository;
import org.soptorshi.repository.search.SupplyOrderSearchRepository;
import org.soptorshi.service.dto.SupplyOrderCriteria;
import org.soptorshi.service.dto.SupplyOrderDTO;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplyOrder entities in the database.
 * The main input is a {@link SupplyOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyOrderDTO} or a {@link Page} of {@link SupplyOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyOrderQueryService extends QueryService<SupplyOrder> {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderQueryService.class);

    private final SupplyOrderRepository supplyOrderRepository;

    private final SupplyOrderMapper supplyOrderMapper;

    private final SupplyOrderSearchRepository supplyOrderSearchRepository;

    public SupplyOrderQueryService(SupplyOrderRepository supplyOrderRepository, SupplyOrderMapper supplyOrderMapper, SupplyOrderSearchRepository supplyOrderSearchRepository) {
        this.supplyOrderRepository = supplyOrderRepository;
        this.supplyOrderMapper = supplyOrderMapper;
        this.supplyOrderSearchRepository = supplyOrderSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyOrderDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyOrderDTO> findByCriteria(SupplyOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyOrder> specification = createSpecification(criteria);
        return supplyOrderMapper.toDto(supplyOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyOrderDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyOrderDTO> findByCriteria(SupplyOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyOrder> specification = createSpecification(criteria);
        return supplyOrderRepository.findAll(specification, page)
            .map(supplyOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyOrder> specification = createSpecification(criteria);
        return supplyOrderRepository.count(specification);
    }

    /**
     * Function to convert SupplyOrderCriteria to a {@link Specification}
     */
    private Specification<SupplyOrder> createSpecification(SupplyOrderCriteria criteria) {
        Specification<SupplyOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyOrder_.id));
            }
            if (criteria.getOrderNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderNo(), SupplyOrder_.orderNo));
            }
            if (criteria.getDateOfOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfOrder(), SupplyOrder_.dateOfOrder));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyOrder_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyOrder_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyOrder_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyOrder_.updatedOn));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), SupplyOrder_.deliveryDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), SupplyOrder_.status));
            }
            if (criteria.getAreaWiseAccumulationRefNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaWiseAccumulationRefNo(), SupplyOrder_.areaWiseAccumulationRefNo));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), SupplyOrder_.remarks));
            }
            if (criteria.getSupplyZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneId(),
                    root -> root.join(SupplyOrder_.supplyZone, JoinType.LEFT).get(SupplyZone_.id)));
            }
            if (criteria.getSupplyZoneManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneManagerId(),
                    root -> root.join(SupplyOrder_.supplyZoneManager, JoinType.LEFT).get(SupplyZoneManager_.id)));
            }
            if (criteria.getSupplyAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaId(),
                    root -> root.join(SupplyOrder_.supplyArea, JoinType.LEFT).get(SupplyArea_.id)));
            }
            if (criteria.getSupplySalesRepresentativeId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplySalesRepresentativeId(),
                    root -> root.join(SupplyOrder_.supplySalesRepresentative, JoinType.LEFT).get(SupplySalesRepresentative_.id)));
            }
            if (criteria.getSupplyAreaManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaManagerId(),
                    root -> root.join(SupplyOrder_.supplyAreaManager, JoinType.LEFT).get(SupplyAreaManager_.id)));
            }
            if (criteria.getSupplyShopId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyShopId(),
                    root -> root.join(SupplyOrder_.supplyShop, JoinType.LEFT).get(SupplyShop_.id)));
            }
        }
        return specification;
    }
}
