package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyChallanRepository;
import org.soptorshi.repository.search.SupplyChallanSearchRepository;
import org.soptorshi.service.dto.SupplyChallanCriteria;
import org.soptorshi.service.dto.SupplyChallanDTO;
import org.soptorshi.service.mapper.SupplyChallanMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplyChallan entities in the database.
 * The main input is a {@link SupplyChallanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyChallanDTO} or a {@link Page} of {@link SupplyChallanDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyChallanQueryService extends QueryService<SupplyChallan> {

    private final Logger log = LoggerFactory.getLogger(SupplyChallanQueryService.class);

    private final SupplyChallanRepository supplyChallanRepository;

    private final SupplyChallanMapper supplyChallanMapper;

    private final SupplyChallanSearchRepository supplyChallanSearchRepository;

    public SupplyChallanQueryService(SupplyChallanRepository supplyChallanRepository, SupplyChallanMapper supplyChallanMapper, SupplyChallanSearchRepository supplyChallanSearchRepository) {
        this.supplyChallanRepository = supplyChallanRepository;
        this.supplyChallanMapper = supplyChallanMapper;
        this.supplyChallanSearchRepository = supplyChallanSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyChallanDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyChallanDTO> findByCriteria(SupplyChallanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyChallan> specification = createSpecification(criteria);
        return supplyChallanMapper.toDto(supplyChallanRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyChallanDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyChallanDTO> findByCriteria(SupplyChallanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyChallan> specification = createSpecification(criteria);
        return supplyChallanRepository.findAll(specification, page)
            .map(supplyChallanMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyChallanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyChallan> specification = createSpecification(criteria);
        return supplyChallanRepository.count(specification);
    }

    /**
     * Function to convert SupplyChallanCriteria to a {@link Specification}
     */
    private Specification<SupplyChallan> createSpecification(SupplyChallanCriteria criteria) {
        Specification<SupplyChallan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyChallan_.id));
            }
            if (criteria.getChallanNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChallanNo(), SupplyChallan_.challanNo));
            }
            if (criteria.getDateOfChallan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfChallan(), SupplyChallan_.dateOfChallan));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), SupplyChallan_.remarks));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyChallan_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyChallan_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyChallan_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyChallan_.updatedOn));
            }
            if (criteria.getSupplyZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneId(),
                    root -> root.join(SupplyChallan_.supplyZone, JoinType.LEFT).get(SupplyZone_.id)));
            }
            if (criteria.getSupplyZoneManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneManagerId(),
                    root -> root.join(SupplyChallan_.supplyZoneManager, JoinType.LEFT).get(SupplyZoneManager_.id)));
            }
            if (criteria.getSupplyAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaId(),
                    root -> root.join(SupplyChallan_.supplyArea, JoinType.LEFT).get(SupplyArea_.id)));
            }
            if (criteria.getSupplyAreaManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaManagerId(),
                    root -> root.join(SupplyChallan_.supplyAreaManager, JoinType.LEFT).get(SupplyAreaManager_.id)));
            }
            if (criteria.getSupplySalesRepresentativeId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplySalesRepresentativeId(),
                    root -> root.join(SupplyChallan_.supplySalesRepresentative, JoinType.LEFT).get(SupplySalesRepresentative_.id)));
            }
            if (criteria.getSupplyShopId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyShopId(),
                    root -> root.join(SupplyChallan_.supplyShop, JoinType.LEFT).get(SupplyShop_.id)));
            }
            if (criteria.getSupplyOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyOrderId(),
                    root -> root.join(SupplyChallan_.supplyOrder, JoinType.LEFT).get(SupplyOrder_.id)));
            }
        }
        return specification;
    }
}
