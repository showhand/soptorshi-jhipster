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

import org.soptorshi.domain.CommercialPackaging;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.CommercialPackagingRepository;
import org.soptorshi.repository.search.CommercialPackagingSearchRepository;
import org.soptorshi.service.dto.CommercialPackagingCriteria;
import org.soptorshi.service.dto.CommercialPackagingDTO;
import org.soptorshi.service.mapper.CommercialPackagingMapper;

/**
 * Service for executing complex queries for CommercialPackaging entities in the database.
 * The main input is a {@link CommercialPackagingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialPackagingDTO} or a {@link Page} of {@link CommercialPackagingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialPackagingQueryService extends QueryService<CommercialPackaging> {

    private final Logger log = LoggerFactory.getLogger(CommercialPackagingQueryService.class);

    private final CommercialPackagingRepository commercialPackagingRepository;

    private final CommercialPackagingMapper commercialPackagingMapper;

    private final CommercialPackagingSearchRepository commercialPackagingSearchRepository;

    public CommercialPackagingQueryService(CommercialPackagingRepository commercialPackagingRepository, CommercialPackagingMapper commercialPackagingMapper, CommercialPackagingSearchRepository commercialPackagingSearchRepository) {
        this.commercialPackagingRepository = commercialPackagingRepository;
        this.commercialPackagingMapper = commercialPackagingMapper;
        this.commercialPackagingSearchRepository = commercialPackagingSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialPackagingDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialPackagingDTO> findByCriteria(CommercialPackagingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialPackaging> specification = createSpecification(criteria);
        return commercialPackagingMapper.toDto(commercialPackagingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialPackagingDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialPackagingDTO> findByCriteria(CommercialPackagingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialPackaging> specification = createSpecification(criteria);
        return commercialPackagingRepository.findAll(specification, page)
            .map(commercialPackagingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialPackagingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialPackaging> specification = createSpecification(criteria);
        return commercialPackagingRepository.count(specification);
    }

    /**
     * Function to convert CommercialPackagingCriteria to a {@link Specification}
     */
    private Specification<CommercialPackaging> createSpecification(CommercialPackagingCriteria criteria) {
        Specification<CommercialPackaging> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialPackaging_.id));
            }
            if (criteria.getConsignmentNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConsignmentNo(), CommercialPackaging_.consignmentNo));
            }
            if (criteria.getConsignmentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConsignmentDate(), CommercialPackaging_.consignmentDate));
            }
            if (criteria.getBrand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrand(), CommercialPackaging_.brand));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialPackaging_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), CommercialPackaging_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialPackaging_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialPackaging_.updatedOn));
            }
            if (criteria.getCommercialPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPurchaseOrderId(),
                    root -> root.join(CommercialPackaging_.commercialPurchaseOrder, JoinType.LEFT).get(CommercialPurchaseOrder_.id)));
            }
        }
        return specification;
    }
}
