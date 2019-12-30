package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.StockInItemRepository;
import org.soptorshi.repository.search.StockInItemSearchRepository;
import org.soptorshi.service.dto.StockInItemCriteria;
import org.soptorshi.service.dto.StockInItemDTO;
import org.soptorshi.service.mapper.StockInItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for StockInItem entities in the database.
 * The main input is a {@link StockInItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockInItemDTO} or a {@link Page} of {@link StockInItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockInItemQueryService extends QueryService<StockInItem> {

    private final Logger log = LoggerFactory.getLogger(StockInItemQueryService.class);

    private final StockInItemRepository stockInItemRepository;

    private final StockInItemMapper stockInItemMapper;

    private final StockInItemSearchRepository stockInItemSearchRepository;

    public StockInItemQueryService(StockInItemRepository stockInItemRepository, StockInItemMapper stockInItemMapper, StockInItemSearchRepository stockInItemSearchRepository) {
        this.stockInItemRepository = stockInItemRepository;
        this.stockInItemMapper = stockInItemMapper;
        this.stockInItemSearchRepository = stockInItemSearchRepository;
    }

    /**
     * Return a {@link List} of {@link StockInItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockInItemDTO> findByCriteria(StockInItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockInItem> specification = createSpecification(criteria);
        return stockInItemMapper.toDto(stockInItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockInItemDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockInItemDTO> findByCriteria(StockInItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockInItem> specification = createSpecification(criteria);
        return stockInItemRepository.findAll(specification, page)
            .map(stockInItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockInItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockInItem> specification = createSpecification(criteria);
        return stockInItemRepository.count(specification);
    }

    /**
     * Function to convert StockInItemCriteria to a {@link Specification}
     */
    private Specification<StockInItem> createSpecification(StockInItemCriteria criteria) {
        Specification<StockInItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StockInItem_.id));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), StockInItem_.quantity));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildSpecification(criteria.getUnit(), StockInItem_.unit));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), StockInItem_.price));
            }
            if (criteria.getContainerCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getContainerCategory(), StockInItem_.containerCategory));
            }
            if (criteria.getContainerTrackingId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContainerTrackingId(), StockInItem_.containerTrackingId));
            }
            if (criteria.getMfgDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMfgDate(), StockInItem_.mfgDate));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), StockInItem_.expiryDate));
            }
            if (criteria.getTypeOfProduct() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeOfProduct(), StockInItem_.typeOfProduct));
            }
            if (criteria.getStockInBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStockInBy(), StockInItem_.stockInBy));
            }
            if (criteria.getStockInDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockInDate(), StockInItem_.stockInDate));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), StockInItem_.remarks));
            }
            if (criteria.getProductCategoriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoriesId(),
                    root -> root.join(StockInItem_.productCategories, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductsId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductsId(),
                    root -> root.join(StockInItem_.products, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getInventoryLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryLocationsId(),
                    root -> root.join(StockInItem_.inventoryLocations, JoinType.LEFT).get(InventoryLocation_.id)));
            }
            if (criteria.getInventorySubLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventorySubLocationsId(),
                    root -> root.join(StockInItem_.inventorySubLocations, JoinType.LEFT).get(InventorySubLocation_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(StockInItem_.vendor, JoinType.LEFT).get(Vendor_.id)));
            }
            if (criteria.getStockInProcessesId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockInProcessesId(),
                    root -> root.join(StockInItem_.stockInProcesses, JoinType.LEFT).get(StockInProcess_.id)));
            }
            if (criteria.getRequisitionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionsId(),
                    root -> root.join(StockInItem_.requisitions, JoinType.LEFT).get(Requisition_.id)));
            }
        }
        return specification;
    }
}
