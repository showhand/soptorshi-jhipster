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

import org.soptorshi.domain.CommercialPoStatus;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.CommercialPoStatusRepository;
import org.soptorshi.repository.search.CommercialPoStatusSearchRepository;
import org.soptorshi.service.dto.CommercialPoStatusCriteria;
import org.soptorshi.service.dto.CommercialPoStatusDTO;
import org.soptorshi.service.mapper.CommercialPoStatusMapper;

/**
 * Service for executing complex queries for CommercialPoStatus entities in the database.
 * The main input is a {@link CommercialPoStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialPoStatusDTO} or a {@link Page} of {@link CommercialPoStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialPoStatusQueryService extends QueryService<CommercialPoStatus> {

    private final Logger log = LoggerFactory.getLogger(CommercialPoStatusQueryService.class);

    private final CommercialPoStatusRepository commercialPoStatusRepository;

    private final CommercialPoStatusMapper commercialPoStatusMapper;

    private final CommercialPoStatusSearchRepository commercialPoStatusSearchRepository;

    public CommercialPoStatusQueryService(CommercialPoStatusRepository commercialPoStatusRepository, CommercialPoStatusMapper commercialPoStatusMapper, CommercialPoStatusSearchRepository commercialPoStatusSearchRepository) {
        this.commercialPoStatusRepository = commercialPoStatusRepository;
        this.commercialPoStatusMapper = commercialPoStatusMapper;
        this.commercialPoStatusSearchRepository = commercialPoStatusSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialPoStatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialPoStatusDTO> findByCriteria(CommercialPoStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialPoStatus> specification = createSpecification(criteria);
        return commercialPoStatusMapper.toDto(commercialPoStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialPoStatusDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialPoStatusDTO> findByCriteria(CommercialPoStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialPoStatus> specification = createSpecification(criteria);
        return commercialPoStatusRepository.findAll(specification, page)
            .map(commercialPoStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialPoStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialPoStatus> specification = createSpecification(criteria);
        return commercialPoStatusRepository.count(specification);
    }

    /**
     * Function to convert CommercialPoStatusCriteria to a {@link Specification}
     */
    private Specification<CommercialPoStatus> createSpecification(CommercialPoStatusCriteria criteria) {
        Specification<CommercialPoStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialPoStatus_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), CommercialPoStatus_.status));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialPoStatus_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), CommercialPoStatus_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialPoStatus_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialPoStatus_.updatedOn));
            }
            if (criteria.getCommercialPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPurchaseOrderId(),
                    root -> root.join(CommercialPoStatus_.commercialPurchaseOrder, JoinType.LEFT).get(CommercialPurchaseOrder_.id)));
            }
        }
        return specification;
    }
}
