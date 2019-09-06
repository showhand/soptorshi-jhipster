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

import org.soptorshi.domain.PredefinedNarration;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.PredefinedNarrationRepository;
import org.soptorshi.repository.search.PredefinedNarrationSearchRepository;
import org.soptorshi.service.dto.PredefinedNarrationCriteria;
import org.soptorshi.service.dto.PredefinedNarrationDTO;
import org.soptorshi.service.mapper.PredefinedNarrationMapper;

/**
 * Service for executing complex queries for PredefinedNarration entities in the database.
 * The main input is a {@link PredefinedNarrationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PredefinedNarrationDTO} or a {@link Page} of {@link PredefinedNarrationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PredefinedNarrationQueryService extends QueryService<PredefinedNarration> {

    private final Logger log = LoggerFactory.getLogger(PredefinedNarrationQueryService.class);

    private final PredefinedNarrationRepository predefinedNarrationRepository;

    private final PredefinedNarrationMapper predefinedNarrationMapper;

    private final PredefinedNarrationSearchRepository predefinedNarrationSearchRepository;

    public PredefinedNarrationQueryService(PredefinedNarrationRepository predefinedNarrationRepository, PredefinedNarrationMapper predefinedNarrationMapper, PredefinedNarrationSearchRepository predefinedNarrationSearchRepository) {
        this.predefinedNarrationRepository = predefinedNarrationRepository;
        this.predefinedNarrationMapper = predefinedNarrationMapper;
        this.predefinedNarrationSearchRepository = predefinedNarrationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PredefinedNarrationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PredefinedNarrationDTO> findByCriteria(PredefinedNarrationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PredefinedNarration> specification = createSpecification(criteria);
        return predefinedNarrationMapper.toDto(predefinedNarrationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PredefinedNarrationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PredefinedNarrationDTO> findByCriteria(PredefinedNarrationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PredefinedNarration> specification = createSpecification(criteria);
        return predefinedNarrationRepository.findAll(specification, page)
            .map(predefinedNarrationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PredefinedNarrationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PredefinedNarration> specification = createSpecification(criteria);
        return predefinedNarrationRepository.count(specification);
    }

    /**
     * Function to convert PredefinedNarrationCriteria to a {@link Specification}
     */
    private Specification<PredefinedNarration> createSpecification(PredefinedNarrationCriteria criteria) {
        Specification<PredefinedNarration> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PredefinedNarration_.id));
            }
            if (criteria.getNarration() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNarration(), PredefinedNarration_.narration));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), PredefinedNarration_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), PredefinedNarration_.modifiedOn));
            }
            if (criteria.getVoucherId() != null) {
                specification = specification.and(buildSpecification(criteria.getVoucherId(),
                    root -> root.join(PredefinedNarration_.voucher, JoinType.LEFT).get(Voucher_.id)));
            }
        }
        return specification;
    }
}
