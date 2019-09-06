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

import org.soptorshi.domain.ConversionFactor;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ConversionFactorRepository;
import org.soptorshi.repository.search.ConversionFactorSearchRepository;
import org.soptorshi.service.dto.ConversionFactorCriteria;
import org.soptorshi.service.dto.ConversionFactorDTO;
import org.soptorshi.service.mapper.ConversionFactorMapper;

/**
 * Service for executing complex queries for ConversionFactor entities in the database.
 * The main input is a {@link ConversionFactorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConversionFactorDTO} or a {@link Page} of {@link ConversionFactorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConversionFactorQueryService extends QueryService<ConversionFactor> {

    private final Logger log = LoggerFactory.getLogger(ConversionFactorQueryService.class);

    private final ConversionFactorRepository conversionFactorRepository;

    private final ConversionFactorMapper conversionFactorMapper;

    private final ConversionFactorSearchRepository conversionFactorSearchRepository;

    public ConversionFactorQueryService(ConversionFactorRepository conversionFactorRepository, ConversionFactorMapper conversionFactorMapper, ConversionFactorSearchRepository conversionFactorSearchRepository) {
        this.conversionFactorRepository = conversionFactorRepository;
        this.conversionFactorMapper = conversionFactorMapper;
        this.conversionFactorSearchRepository = conversionFactorSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ConversionFactorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConversionFactorDTO> findByCriteria(ConversionFactorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConversionFactor> specification = createSpecification(criteria);
        return conversionFactorMapper.toDto(conversionFactorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConversionFactorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConversionFactorDTO> findByCriteria(ConversionFactorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConversionFactor> specification = createSpecification(criteria);
        return conversionFactorRepository.findAll(specification, page)
            .map(conversionFactorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConversionFactorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConversionFactor> specification = createSpecification(criteria);
        return conversionFactorRepository.count(specification);
    }

    /**
     * Function to convert ConversionFactorCriteria to a {@link Specification}
     */
    private Specification<ConversionFactor> createSpecification(ConversionFactorCriteria criteria) {
        Specification<ConversionFactor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ConversionFactor_.id));
            }
            if (criteria.getCovFactor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCovFactor(), ConversionFactor_.covFactor));
            }
            if (criteria.getRcovFactor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRcovFactor(), ConversionFactor_.rcovFactor));
            }
            if (criteria.getBcovFactor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBcovFactor(), ConversionFactor_.bcovFactor));
            }
            if (criteria.getRbcovFactor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRbcovFactor(), ConversionFactor_.rbcovFactor));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ConversionFactor_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), ConversionFactor_.modifiedOn));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(ConversionFactor_.currency, JoinType.LEFT).get(Currency_.id)));
            }
        }
        return specification;
    }
}
