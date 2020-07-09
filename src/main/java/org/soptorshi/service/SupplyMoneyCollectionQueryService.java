package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyMoneyCollectionRepository;
import org.soptorshi.repository.search.SupplyMoneyCollectionSearchRepository;
import org.soptorshi.service.dto.SupplyMoneyCollectionCriteria;
import org.soptorshi.service.dto.SupplyMoneyCollectionDTO;
import org.soptorshi.service.mapper.SupplyMoneyCollectionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplyMoneyCollection entities in the database.
 * The main input is a {@link SupplyMoneyCollectionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyMoneyCollectionDTO} or a {@link Page} of {@link SupplyMoneyCollectionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyMoneyCollectionQueryService extends QueryService<SupplyMoneyCollection> {

    private final Logger log = LoggerFactory.getLogger(SupplyMoneyCollectionQueryService.class);

    private final SupplyMoneyCollectionRepository supplyMoneyCollectionRepository;

    private final SupplyMoneyCollectionMapper supplyMoneyCollectionMapper;

    private final SupplyMoneyCollectionSearchRepository supplyMoneyCollectionSearchRepository;

    public SupplyMoneyCollectionQueryService(SupplyMoneyCollectionRepository supplyMoneyCollectionRepository, SupplyMoneyCollectionMapper supplyMoneyCollectionMapper, SupplyMoneyCollectionSearchRepository supplyMoneyCollectionSearchRepository) {
        this.supplyMoneyCollectionRepository = supplyMoneyCollectionRepository;
        this.supplyMoneyCollectionMapper = supplyMoneyCollectionMapper;
        this.supplyMoneyCollectionSearchRepository = supplyMoneyCollectionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyMoneyCollectionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyMoneyCollectionDTO> findByCriteria(SupplyMoneyCollectionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyMoneyCollection> specification = createSpecification(criteria);
        return supplyMoneyCollectionMapper.toDto(supplyMoneyCollectionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyMoneyCollectionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyMoneyCollectionDTO> findByCriteria(SupplyMoneyCollectionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyMoneyCollection> specification = createSpecification(criteria);
        return supplyMoneyCollectionRepository.findAll(specification, page)
            .map(supplyMoneyCollectionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyMoneyCollectionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyMoneyCollection> specification = createSpecification(criteria);
        return supplyMoneyCollectionRepository.count(specification);
    }

    /**
     * Function to convert SupplyMoneyCollectionCriteria to a {@link Specification}
     */
    private Specification<SupplyMoneyCollection> createSpecification(SupplyMoneyCollectionCriteria criteria) {
        Specification<SupplyMoneyCollection> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyMoneyCollection_.id));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), SupplyMoneyCollection_.totalAmount));
            }
            if (criteria.getReceivedAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedAmount(), SupplyMoneyCollection_.receivedAmount));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), SupplyMoneyCollection_.remarks));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyMoneyCollection_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyMoneyCollection_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyMoneyCollection_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyMoneyCollection_.updatedOn));
            }
            if (criteria.getSupplyZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneId(),
                    root -> root.join(SupplyMoneyCollection_.supplyZone, JoinType.LEFT).get(SupplyZone_.id)));
            }
            if (criteria.getSupplyZoneManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneManagerId(),
                    root -> root.join(SupplyMoneyCollection_.supplyZoneManager, JoinType.LEFT).get(SupplyZoneManager_.id)));
            }
            if (criteria.getSupplyAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaId(),
                    root -> root.join(SupplyMoneyCollection_.supplyArea, JoinType.LEFT).get(SupplyArea_.id)));
            }
            if (criteria.getSupplyAreaManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaManagerId(),
                    root -> root.join(SupplyMoneyCollection_.supplyAreaManager, JoinType.LEFT).get(SupplyAreaManager_.id)));
            }
            if (criteria.getSupplySalesRepresentativeId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplySalesRepresentativeId(),
                    root -> root.join(SupplyMoneyCollection_.supplySalesRepresentative, JoinType.LEFT).get(SupplySalesRepresentative_.id)));
            }
            if (criteria.getSupplyShopId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyShopId(),
                    root -> root.join(SupplyMoneyCollection_.supplyShop, JoinType.LEFT).get(SupplyShop_.id)));
            }
            if (criteria.getSupplyOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyOrderId(),
                    root -> root.join(SupplyMoneyCollection_.supplyOrder, JoinType.LEFT).get(SupplyOrder_.id)));
            }
        }
        return specification;
    }
}
