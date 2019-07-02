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

import org.soptorshi.domain.TermsAndConditions;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.TermsAndConditionsRepository;
import org.soptorshi.repository.search.TermsAndConditionsSearchRepository;
import org.soptorshi.service.dto.TermsAndConditionsCriteria;
import org.soptorshi.service.dto.TermsAndConditionsDTO;
import org.soptorshi.service.mapper.TermsAndConditionsMapper;

/**
 * Service for executing complex queries for TermsAndConditions entities in the database.
 * The main input is a {@link TermsAndConditionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TermsAndConditionsDTO} or a {@link Page} of {@link TermsAndConditionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TermsAndConditionsQueryService extends QueryService<TermsAndConditions> {

    private final Logger log = LoggerFactory.getLogger(TermsAndConditionsQueryService.class);

    private final TermsAndConditionsRepository termsAndConditionsRepository;

    private final TermsAndConditionsMapper termsAndConditionsMapper;

    private final TermsAndConditionsSearchRepository termsAndConditionsSearchRepository;

    public TermsAndConditionsQueryService(TermsAndConditionsRepository termsAndConditionsRepository, TermsAndConditionsMapper termsAndConditionsMapper, TermsAndConditionsSearchRepository termsAndConditionsSearchRepository) {
        this.termsAndConditionsRepository = termsAndConditionsRepository;
        this.termsAndConditionsMapper = termsAndConditionsMapper;
        this.termsAndConditionsSearchRepository = termsAndConditionsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TermsAndConditionsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TermsAndConditionsDTO> findByCriteria(TermsAndConditionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TermsAndConditions> specification = createSpecification(criteria);
        return termsAndConditionsMapper.toDto(termsAndConditionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TermsAndConditionsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TermsAndConditionsDTO> findByCriteria(TermsAndConditionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TermsAndConditions> specification = createSpecification(criteria);
        return termsAndConditionsRepository.findAll(specification, page)
            .map(termsAndConditionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TermsAndConditionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TermsAndConditions> specification = createSpecification(criteria);
        return termsAndConditionsRepository.count(specification);
    }

    /**
     * Function to convert TermsAndConditionsCriteria to a {@link Specification}
     */
    private Specification<TermsAndConditions> createSpecification(TermsAndConditionsCriteria criteria) {
        Specification<TermsAndConditions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TermsAndConditions_.id));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), TermsAndConditions_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), TermsAndConditions_.modifiedOn));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPurchaseOrderId(),
                    root -> root.join(TermsAndConditions_.purchaseOrder, JoinType.LEFT).get(PurchaseOrder_.id)));
            }
        }
        return specification;
    }
}
