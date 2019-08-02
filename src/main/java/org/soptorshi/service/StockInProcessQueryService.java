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

import org.soptorshi.domain.StockInProcess;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.StockInProcessRepository;
import org.soptorshi.repository.search.StockInProcessSearchRepository;
import org.soptorshi.service.dto.StockInProcessCriteria;
import org.soptorshi.service.dto.StockInProcessDTO;
import org.soptorshi.service.mapper.StockInProcessMapper;

/**
 * Service for executing complex queries for StockInProcess entities in the database.
 * The main input is a {@link StockInProcessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockInProcessDTO} or a {@link Page} of {@link StockInProcessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockInProcessQueryService extends QueryService<StockInProcess> {

    private final Logger log = LoggerFactory.getLogger(StockInProcessQueryService.class);

    private final StockInProcessRepository stockInProcessRepository;

    private final StockInProcessMapper stockInProcessMapper;

    private final StockInProcessSearchRepository stockInProcessSearchRepository;

    public StockInProcessQueryService(StockInProcessRepository stockInProcessRepository, StockInProcessMapper stockInProcessMapper, StockInProcessSearchRepository stockInProcessSearchRepository) {
        this.stockInProcessRepository = stockInProcessRepository;
        this.stockInProcessMapper = stockInProcessMapper;
        this.stockInProcessSearchRepository = stockInProcessSearchRepository;
    }

    /**
     * Return a {@link List} of {@link StockInProcessDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockInProcessDTO> findByCriteria(StockInProcessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockInProcess> specification = createSpecification(criteria);
        return stockInProcessMapper.toDto(stockInProcessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockInProcessDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockInProcessDTO> findByCriteria(StockInProcessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockInProcess> specification = createSpecification(criteria);
        return stockInProcessRepository.findAll(specification, page)
            .map(stockInProcessMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockInProcessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockInProcess> specification = createSpecification(criteria);
        return stockInProcessRepository.count(specification);
    }

    /**
     * Function to convert StockInProcessCriteria to a {@link Specification}
     */
    private Specification<StockInProcess> createSpecification(StockInProcessCriteria criteria) {
        Specification<StockInProcess> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StockInProcess_.id));
            }
            if (criteria.getTotalQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalQuantity(), StockInProcess_.totalQuantity));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildSpecification(criteria.getUnit(), StockInProcess_.unit));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), StockInProcess_.unitPrice));
            }
            if (criteria.getTotalContainer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalContainer(), StockInProcess_.totalContainer));
            }
            if (criteria.getContainerCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getContainerCategory(), StockInProcess_.containerCategory));
            }
            if (criteria.getContainerTrackingId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContainerTrackingId(), StockInProcess_.containerTrackingId));
            }
            if (criteria.getItemPerContainer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemPerContainer(), StockInProcess_.itemPerContainer));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), StockInProcess_.expiryDate));
            }
            if (criteria.getStockInBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStockInBy(), StockInProcess_.stockInBy));
            }
            if (criteria.getStockInDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockInDate(), StockInProcess_.stockInDate));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurchaseOrderId(), StockInProcess_.purchaseOrderId));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), StockInProcess_.remarks));
            }
            if (criteria.getItemCategoriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemCategoriesId(),
                    root -> root.join(StockInProcess_.itemCategories, JoinType.LEFT).get(ItemCategory_.id)));
            }
            if (criteria.getItemSubCategoriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getItemSubCategoriesId(),
                    root -> root.join(StockInProcess_.itemSubCategories, JoinType.LEFT).get(ItemSubCategory_.id)));
            }
            if (criteria.getInventoryLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryLocationsId(),
                    root -> root.join(StockInProcess_.inventoryLocations, JoinType.LEFT).get(InventoryLocation_.id)));
            }
            if (criteria.getInventorySubLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventorySubLocationsId(),
                    root -> root.join(StockInProcess_.inventorySubLocations, JoinType.LEFT).get(InventorySubLocation_.id)));
            }
            if (criteria.getManufacturersId() != null) {
                specification = specification.and(buildSpecification(criteria.getManufacturersId(),
                    root -> root.join(StockInProcess_.manufacturers, JoinType.LEFT).get(Manufacturer_.id)));
            }
        }
        return specification;
    }
}
