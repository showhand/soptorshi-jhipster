package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyAreaWiseAccumulationRepository;
import org.soptorshi.repository.search.SupplyAreaWiseAccumulationSearchRepository;
import org.soptorshi.service.dto.SupplyAreaWiseAccumulationCriteria;
import org.soptorshi.service.dto.SupplyAreaWiseAccumulationDTO;
import org.soptorshi.service.mapper.SupplyAreaWiseAccumulationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplyAreaWiseAccumulation entities in the database.
 * The main input is a {@link SupplyAreaWiseAccumulationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyAreaWiseAccumulationDTO} or a {@link Page} of {@link SupplyAreaWiseAccumulationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyAreaWiseAccumulationQueryService extends QueryService<SupplyAreaWiseAccumulation> {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaWiseAccumulationQueryService.class);

    private final SupplyAreaWiseAccumulationRepository supplyAreaWiseAccumulationRepository;

    private final SupplyAreaWiseAccumulationMapper supplyAreaWiseAccumulationMapper;

    private final SupplyAreaWiseAccumulationSearchRepository supplyAreaWiseAccumulationSearchRepository;

    public SupplyAreaWiseAccumulationQueryService(SupplyAreaWiseAccumulationRepository supplyAreaWiseAccumulationRepository, SupplyAreaWiseAccumulationMapper supplyAreaWiseAccumulationMapper, SupplyAreaWiseAccumulationSearchRepository supplyAreaWiseAccumulationSearchRepository) {
        this.supplyAreaWiseAccumulationRepository = supplyAreaWiseAccumulationRepository;
        this.supplyAreaWiseAccumulationMapper = supplyAreaWiseAccumulationMapper;
        this.supplyAreaWiseAccumulationSearchRepository = supplyAreaWiseAccumulationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyAreaWiseAccumulationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyAreaWiseAccumulationDTO> findByCriteria(SupplyAreaWiseAccumulationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyAreaWiseAccumulation> specification = createSpecification(criteria);
        return supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyAreaWiseAccumulationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyAreaWiseAccumulationDTO> findByCriteria(SupplyAreaWiseAccumulationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyAreaWiseAccumulation> specification = createSpecification(criteria);
        return supplyAreaWiseAccumulationRepository.findAll(specification, page)
            .map(supplyAreaWiseAccumulationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyAreaWiseAccumulationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyAreaWiseAccumulation> specification = createSpecification(criteria);
        return supplyAreaWiseAccumulationRepository.count(specification);
    }

    /**
     * Function to convert SupplyAreaWiseAccumulationCriteria to a {@link Specification}
     */
    private Specification<SupplyAreaWiseAccumulation> createSpecification(SupplyAreaWiseAccumulationCriteria criteria) {
        Specification<SupplyAreaWiseAccumulation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyAreaWiseAccumulation_.id));
            }
            if (criteria.getAreaWiseAccumulationRefNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaWiseAccumulationRefNo(), SupplyAreaWiseAccumulation_.areaWiseAccumulationRefNo));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), SupplyAreaWiseAccumulation_.quantity));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), SupplyAreaWiseAccumulation_.price));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), SupplyAreaWiseAccumulation_.status));
            }
            if (criteria.getZoneWiseAccumulationRefNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZoneWiseAccumulationRefNo(), SupplyAreaWiseAccumulation_.zoneWiseAccumulationRefNo));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), SupplyAreaWiseAccumulation_.remarks));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyAreaWiseAccumulation_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyAreaWiseAccumulation_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyAreaWiseAccumulation_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyAreaWiseAccumulation_.updatedOn));
            }
            if (criteria.getSupplyZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneId(),
                    root -> root.join(SupplyAreaWiseAccumulation_.supplyZone, JoinType.LEFT).get(SupplyZone_.id)));
            }
            if (criteria.getSupplyZoneManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneManagerId(),
                    root -> root.join(SupplyAreaWiseAccumulation_.supplyZoneManager, JoinType.LEFT).get(SupplyZoneManager_.id)));
            }
            if (criteria.getSupplyAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaId(),
                    root -> root.join(SupplyAreaWiseAccumulation_.supplyArea, JoinType.LEFT).get(SupplyArea_.id)));
            }
            if (criteria.getSupplyAreaManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaManagerId(),
                    root -> root.join(SupplyAreaWiseAccumulation_.supplyAreaManager, JoinType.LEFT).get(SupplyAreaManager_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(SupplyAreaWiseAccumulation_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(SupplyAreaWiseAccumulation_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
