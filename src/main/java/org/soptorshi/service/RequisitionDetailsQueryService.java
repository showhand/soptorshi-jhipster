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

import org.soptorshi.domain.RequisitionDetails;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.RequisitionDetailsRepository;
import org.soptorshi.repository.search.RequisitionDetailsSearchRepository;
import org.soptorshi.service.dto.RequisitionDetailsCriteria;
import org.soptorshi.service.dto.RequisitionDetailsDTO;
import org.soptorshi.service.mapper.RequisitionDetailsMapper;

/**
 * Service for executing complex queries for RequisitionDetails entities in the database.
 * The main input is a {@link RequisitionDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RequisitionDetailsDTO} or a {@link Page} of {@link RequisitionDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RequisitionDetailsQueryService extends QueryService<RequisitionDetails> {

    private final Logger log = LoggerFactory.getLogger(RequisitionDetailsQueryService.class);

    private final RequisitionDetailsRepository requisitionDetailsRepository;

    private final RequisitionDetailsMapper requisitionDetailsMapper;

    private final RequisitionDetailsSearchRepository requisitionDetailsSearchRepository;

    public RequisitionDetailsQueryService(RequisitionDetailsRepository requisitionDetailsRepository, RequisitionDetailsMapper requisitionDetailsMapper, RequisitionDetailsSearchRepository requisitionDetailsSearchRepository) {
        this.requisitionDetailsRepository = requisitionDetailsRepository;
        this.requisitionDetailsMapper = requisitionDetailsMapper;
        this.requisitionDetailsSearchRepository = requisitionDetailsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RequisitionDetailsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RequisitionDetailsDTO> findByCriteria(RequisitionDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RequisitionDetails> specification = createSpecification(criteria);
        return requisitionDetailsMapper.toDto(requisitionDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RequisitionDetailsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RequisitionDetailsDTO> findByCriteria(RequisitionDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RequisitionDetails> specification = createSpecification(criteria);
        return requisitionDetailsRepository.findAll(specification, page)
            .map(requisitionDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RequisitionDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RequisitionDetails> specification = createSpecification(criteria);
        return requisitionDetailsRepository.count(specification);
    }

    /**
     * Function to convert RequisitionDetailsCriteria to a {@link Specification}
     */
    private Specification<RequisitionDetails> createSpecification(RequisitionDetailsCriteria criteria) {
        Specification<RequisitionDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RequisitionDetails_.id));
            }
            if (criteria.getRequiredOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequiredOn(), RequisitionDetails_.requiredOn));
            }
            if (criteria.getEstimatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedDate(), RequisitionDetails_.estimatedDate));
            }
            if (criteria.getUom() != null) {
                specification = specification.and(buildSpecification(criteria.getUom(), RequisitionDetails_.uom));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnit(), RequisitionDetails_.unit));
            }
            if (criteria.getUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitPrice(), RequisitionDetails_.unitPrice));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), RequisitionDetails_.quantity));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), RequisitionDetails_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), RequisitionDetails_.modifiedOn));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(RequisitionDetails_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(RequisitionDetails_.requisition, JoinType.LEFT).get(Requisition_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(RequisitionDetails_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
