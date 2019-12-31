package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.StockOutItemRepository;
import org.soptorshi.repository.search.StockOutItemSearchRepository;
import org.soptorshi.service.dto.StockOutItemCriteria;
import org.soptorshi.service.dto.StockOutItemDTO;
import org.soptorshi.service.mapper.StockOutItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for StockOutItem entities in the database.
 * The main input is a {@link StockOutItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockOutItemDTO} or a {@link Page} of {@link StockOutItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockOutItemQueryService extends QueryService<StockOutItem> {

    private final Logger log = LoggerFactory.getLogger(StockOutItemQueryService.class);

    private final StockOutItemRepository stockOutItemRepository;

    private final StockOutItemMapper stockOutItemMapper;

    private final StockOutItemSearchRepository stockOutItemSearchRepository;

    public StockOutItemQueryService(StockOutItemRepository stockOutItemRepository, StockOutItemMapper stockOutItemMapper, StockOutItemSearchRepository stockOutItemSearchRepository) {
        this.stockOutItemRepository = stockOutItemRepository;
        this.stockOutItemMapper = stockOutItemMapper;
        this.stockOutItemSearchRepository = stockOutItemSearchRepository;
    }

    /**
     * Return a {@link List} of {@link StockOutItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockOutItemDTO> findByCriteria(StockOutItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockOutItem> specification = createSpecification(criteria);
        return stockOutItemMapper.toDto(stockOutItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockOutItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockOutItemDTO> findByCriteria(StockOutItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockOutItem> specification = createSpecification(criteria);
        return stockOutItemRepository.findAll(specification, page)
            .map(stockOutItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockOutItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockOutItem> specification = createSpecification(criteria);
        return stockOutItemRepository.count(specification);
    }

    /**
     * Function to convert StockOutItemCriteria to a {@link Specification}
     */
    private Specification<StockOutItem> createSpecification(StockOutItemCriteria criteria) {
        Specification<StockOutItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StockOutItem_.id));
            }
            if (criteria.getContainerTrackingId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContainerTrackingId(), StockOutItem_.containerTrackingId));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), StockOutItem_.quantity));
            }
            if (criteria.getStockOutBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStockOutBy(), StockOutItem_.stockOutBy));
            }
            if (criteria.getStockOutDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockOutDate(), StockOutItem_.stockOutDate));
            }
            if (criteria.getReceiverId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiverId(), StockOutItem_.receiverId));
            }
            if (criteria.getReceivingPlace() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceivingPlace(), StockOutItem_.receivingPlace));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), StockOutItem_.remarks));
            }
            if (criteria.getProductCategoriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoriesId(),
                    root -> root.join(StockOutItem_.productCategories, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductsId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductsId(),
                    root -> root.join(StockOutItem_.products, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getInventoryLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryLocationsId(),
                    root -> root.join(StockOutItem_.inventoryLocations, JoinType.LEFT).get(InventoryLocation_.id)));
            }
            if (criteria.getInventorySubLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventorySubLocationsId(),
                    root -> root.join(StockOutItem_.inventorySubLocations, JoinType.LEFT).get(InventorySubLocation_.id)));
            }
            if (criteria.getStockInItemsId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockInItemsId(),
                    root -> root.join(StockOutItem_.stockInItems, JoinType.LEFT).get(StockInItem_.id)));
            }
        }
        return specification;
    }
}
