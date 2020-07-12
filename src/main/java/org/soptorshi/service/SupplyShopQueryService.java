package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyShopRepository;
import org.soptorshi.repository.search.SupplyShopSearchRepository;
import org.soptorshi.service.dto.SupplyShopCriteria;
import org.soptorshi.service.dto.SupplyShopDTO;
import org.soptorshi.service.mapper.SupplyShopMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplyShop entities in the database.
 * The main input is a {@link SupplyShopCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyShopDTO} or a {@link Page} of {@link SupplyShopDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyShopQueryService extends QueryService<SupplyShop> {

    private final Logger log = LoggerFactory.getLogger(SupplyShopQueryService.class);

    private final SupplyShopRepository supplyShopRepository;

    private final SupplyShopMapper supplyShopMapper;

    private final SupplyShopSearchRepository supplyShopSearchRepository;

    public SupplyShopQueryService(SupplyShopRepository supplyShopRepository, SupplyShopMapper supplyShopMapper, SupplyShopSearchRepository supplyShopSearchRepository) {
        this.supplyShopRepository = supplyShopRepository;
        this.supplyShopMapper = supplyShopMapper;
        this.supplyShopSearchRepository = supplyShopSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyShopDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyShopDTO> findByCriteria(SupplyShopCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyShop> specification = createSpecification(criteria);
        return supplyShopMapper.toDto(supplyShopRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyShopDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyShopDTO> findByCriteria(SupplyShopCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyShop> specification = createSpecification(criteria);
        return supplyShopRepository.findAll(specification, page)
            .map(supplyShopMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyShopCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyShop> specification = createSpecification(criteria);
        return supplyShopRepository.count(specification);
    }

    /**
     * Function to convert SupplyShopCriteria to a {@link Specification}
     */
    private Specification<SupplyShop> createSpecification(SupplyShopCriteria criteria) {
        Specification<SupplyShop> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyShop_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SupplyShop_.name));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), SupplyShop_.contact));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), SupplyShop_.email));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), SupplyShop_.address));
            }
            if (criteria.getAdditionalInformation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdditionalInformation(), SupplyShop_.additionalInformation));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyShop_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyShop_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyShop_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyShop_.updatedOn));
            }
            if (criteria.getSupplyZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneId(),
                    root -> root.join(SupplyShop_.supplyZone, JoinType.LEFT).get(SupplyZone_.id)));
            }
            if (criteria.getSupplyAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaId(),
                    root -> root.join(SupplyShop_.supplyArea, JoinType.LEFT).get(SupplyArea_.id)));
            }
            if (criteria.getSupplyZoneManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneManagerId(),
                    root -> root.join(SupplyShop_.supplyZoneManager, JoinType.LEFT).get(SupplyZoneManager_.id)));
            }
            if (criteria.getSupplyAreaManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaManagerId(),
                    root -> root.join(SupplyShop_.supplyAreaManager, JoinType.LEFT).get(SupplyAreaManager_.id)));
            }
            if (criteria.getSupplySalesRepresentativeId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplySalesRepresentativeId(),
                    root -> root.join(SupplyShop_.supplySalesRepresentative, JoinType.LEFT).get(SupplySalesRepresentative_.id)));
            }
        }
        return specification;
    }
}
