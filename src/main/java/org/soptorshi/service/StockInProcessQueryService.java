package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.StockInProcessRepository;
import org.soptorshi.repository.search.StockInProcessSearchRepository;
import org.soptorshi.service.dto.StockInProcessCriteria;
import org.soptorshi.service.dto.StockInProcessDTO;
import org.soptorshi.service.mapper.StockInProcessMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

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
            if (criteria.getQuantityPerContainer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuantityPerContainer(), StockInProcess_.quantityPerContainer));
            }
            if (criteria.getMfgDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMfgDate(), StockInProcess_.mfgDate));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), StockInProcess_.expiryDate));
            }
            if (criteria.getTypeOfProduct() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeOfProduct(), StockInProcess_.typeOfProduct));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), StockInProcess_.status));
            }
            if (criteria.getProcessStartedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProcessStartedBy(), StockInProcess_.processStartedBy));
            }
            if (criteria.getProcessStartedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessStartedOn(), StockInProcess_.processStartedOn));
            }
            if (criteria.getStockInBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStockInBy(), StockInProcess_.stockInBy));
            }
            if (criteria.getStockInDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockInDate(), StockInProcess_.stockInDate));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), StockInProcess_.remarks));
            }
            if (criteria.getProductCategoriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoriesId(),
                    root -> root.join(StockInProcess_.productCategories, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductsId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductsId(),
                    root -> root.join(StockInProcess_.products, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getInventoryLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryLocationsId(),
                    root -> root.join(StockInProcess_.inventoryLocations, JoinType.LEFT).get(InventoryLocation_.id)));
            }
            if (criteria.getInventorySubLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventorySubLocationsId(),
                    root -> root.join(StockInProcess_.inventorySubLocations, JoinType.LEFT).get(InventorySubLocation_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(StockInProcess_.vendor, JoinType.LEFT).get(Vendor_.id)));
            }
            if (criteria.getRequisitionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionsId(),
                    root -> root.join(StockInProcess_.requisitions, JoinType.LEFT).get(Requisition_.id)));
            }
        }
        return specification;
    }
}
