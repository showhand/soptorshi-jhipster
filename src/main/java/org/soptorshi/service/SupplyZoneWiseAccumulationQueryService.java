package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyZoneWiseAccumulationRepository;
import org.soptorshi.repository.search.SupplyZoneWiseAccumulationSearchRepository;
import org.soptorshi.service.dto.SupplyZoneWiseAccumulationCriteria;
import org.soptorshi.service.dto.SupplyZoneWiseAccumulationDTO;
import org.soptorshi.service.mapper.SupplyZoneWiseAccumulationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplyZoneWiseAccumulation entities in the database.
 * The main input is a {@link SupplyZoneWiseAccumulationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyZoneWiseAccumulationDTO} or a {@link Page} of {@link SupplyZoneWiseAccumulationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyZoneWiseAccumulationQueryService extends QueryService<SupplyZoneWiseAccumulation> {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneWiseAccumulationQueryService.class);

    private final SupplyZoneWiseAccumulationRepository supplyZoneWiseAccumulationRepository;

    private final SupplyZoneWiseAccumulationMapper supplyZoneWiseAccumulationMapper;

    private final SupplyZoneWiseAccumulationSearchRepository supplyZoneWiseAccumulationSearchRepository;

    public SupplyZoneWiseAccumulationQueryService(SupplyZoneWiseAccumulationRepository supplyZoneWiseAccumulationRepository, SupplyZoneWiseAccumulationMapper supplyZoneWiseAccumulationMapper, SupplyZoneWiseAccumulationSearchRepository supplyZoneWiseAccumulationSearchRepository) {
        this.supplyZoneWiseAccumulationRepository = supplyZoneWiseAccumulationRepository;
        this.supplyZoneWiseAccumulationMapper = supplyZoneWiseAccumulationMapper;
        this.supplyZoneWiseAccumulationSearchRepository = supplyZoneWiseAccumulationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyZoneWiseAccumulationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyZoneWiseAccumulationDTO> findByCriteria(SupplyZoneWiseAccumulationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyZoneWiseAccumulation> specification = createSpecification(criteria);
        return supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyZoneWiseAccumulationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyZoneWiseAccumulationDTO> findByCriteria(SupplyZoneWiseAccumulationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyZoneWiseAccumulation> specification = createSpecification(criteria);
        return supplyZoneWiseAccumulationRepository.findAll(specification, page)
            .map(supplyZoneWiseAccumulationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyZoneWiseAccumulationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyZoneWiseAccumulation> specification = createSpecification(criteria);
        return supplyZoneWiseAccumulationRepository.count(specification);
    }

    /**
     * Function to convert SupplyZoneWiseAccumulationCriteria to a {@link Specification}
     */
    private Specification<SupplyZoneWiseAccumulation> createSpecification(SupplyZoneWiseAccumulationCriteria criteria) {
        Specification<SupplyZoneWiseAccumulation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyZoneWiseAccumulation_.id));
            }
            if (criteria.getZoneWiseAccumulationRefNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getZoneWiseAccumulationRefNo(), SupplyZoneWiseAccumulation_.zoneWiseAccumulationRefNo));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), SupplyZoneWiseAccumulation_.quantity));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), SupplyZoneWiseAccumulation_.price));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), SupplyZoneWiseAccumulation_.status));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), SupplyZoneWiseAccumulation_.remarks));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyZoneWiseAccumulation_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyZoneWiseAccumulation_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyZoneWiseAccumulation_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyZoneWiseAccumulation_.updatedOn));
            }
            if (criteria.getSupplyZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneId(),
                    root -> root.join(SupplyZoneWiseAccumulation_.supplyZone, JoinType.LEFT).get(SupplyZone_.id)));
            }
            if (criteria.getSupplyZoneManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneManagerId(),
                    root -> root.join(SupplyZoneWiseAccumulation_.supplyZoneManager, JoinType.LEFT).get(SupplyZoneManager_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(SupplyZoneWiseAccumulation_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(SupplyZoneWiseAccumulation_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
