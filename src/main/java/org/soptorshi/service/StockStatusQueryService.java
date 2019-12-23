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

import org.soptorshi.domain.StockStatus;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.StockStatusRepository;
import org.soptorshi.repository.search.StockStatusSearchRepository;
import org.soptorshi.service.dto.StockStatusCriteria;
import org.soptorshi.service.dto.StockStatusDTO;
import org.soptorshi.service.mapper.StockStatusMapper;

/**
 * Service for executing complex queries for StockStatus entities in the database.
 * The main input is a {@link StockStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockStatusDTO} or a {@link Page} of {@link StockStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockStatusQueryService extends QueryService<StockStatus> {

    private final Logger log = LoggerFactory.getLogger(StockStatusQueryService.class);

    private final StockStatusRepository stockStatusRepository;

    private final StockStatusMapper stockStatusMapper;

    private final StockStatusSearchRepository stockStatusSearchRepository;

    public StockStatusQueryService(StockStatusRepository stockStatusRepository, StockStatusMapper stockStatusMapper, StockStatusSearchRepository stockStatusSearchRepository) {
        this.stockStatusRepository = stockStatusRepository;
        this.stockStatusMapper = stockStatusMapper;
        this.stockStatusSearchRepository = stockStatusSearchRepository;
    }

    /**
     * Return a {@link List} of {@link StockStatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockStatusDTO> findByCriteria(StockStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockStatus> specification = createSpecification(criteria);
        return stockStatusMapper.toDto(stockStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockStatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockStatusDTO> findByCriteria(StockStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockStatus> specification = createSpecification(criteria);
        return stockStatusRepository.findAll(specification, page)
            .map(stockStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockStatus> specification = createSpecification(criteria);
        return stockStatusRepository.count(specification);
    }

    /**
     * Function to convert StockStatusCriteria to a {@link Specification}
     */
    private Specification<StockStatus> createSpecification(StockStatusCriteria criteria) {
        Specification<StockStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StockStatus_.id));
            }
            if (criteria.getContainerTrackingId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContainerTrackingId(), StockStatus_.containerTrackingId));
            }
            if (criteria.getTotalQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalQuantity(), StockStatus_.totalQuantity));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildSpecification(criteria.getUnit(), StockStatus_.unit));
            }
            if (criteria.getAvailableQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailableQuantity(), StockStatus_.availableQuantity));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), StockStatus_.totalPrice));
            }
            if (criteria.getAvailablePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailablePrice(), StockStatus_.availablePrice));
            }
            if (criteria.getStockInBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStockInBy(), StockStatus_.stockInBy));
            }
            if (criteria.getStockInDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockInDate(), StockStatus_.stockInDate));
            }
            if (criteria.getStockInItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockInItemId(),
                    root -> root.join(StockStatus_.stockInItem, JoinType.LEFT).get(StockInItem_.id)));
            }
            if (criteria.getProductCategoriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoriesId(),
                    root -> root.join(StockStatus_.productCategories, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductsId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductsId(),
                    root -> root.join(StockStatus_.products, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getInventoryLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryLocationsId(),
                    root -> root.join(StockStatus_.inventoryLocations, JoinType.LEFT).get(InventoryLocation_.id)));
            }
            if (criteria.getInventorySubLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventorySubLocationsId(),
                    root -> root.join(StockStatus_.inventorySubLocations, JoinType.LEFT).get(InventorySubLocation_.id)));
            }
        }
        return specification;
    }
}
