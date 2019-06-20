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

import org.soptorshi.domain.Advance;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.AdvanceRepository;
import org.soptorshi.repository.search.AdvanceSearchRepository;
import org.soptorshi.service.dto.AdvanceCriteria;
import org.soptorshi.service.dto.AdvanceDTO;
import org.soptorshi.service.mapper.AdvanceMapper;

/**
 * Service for executing complex queries for Advance entities in the database.
 * The main input is a {@link AdvanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdvanceDTO} or a {@link Page} of {@link AdvanceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdvanceQueryService extends QueryService<Advance> {

    private final Logger log = LoggerFactory.getLogger(AdvanceQueryService.class);

    private final AdvanceRepository advanceRepository;

    private final AdvanceMapper advanceMapper;

    private final AdvanceSearchRepository advanceSearchRepository;

    public AdvanceQueryService(AdvanceRepository advanceRepository, AdvanceMapper advanceMapper, AdvanceSearchRepository advanceSearchRepository) {
        this.advanceRepository = advanceRepository;
        this.advanceMapper = advanceMapper;
        this.advanceSearchRepository = advanceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AdvanceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdvanceDTO> findByCriteria(AdvanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Advance> specification = createSpecification(criteria);
        return advanceMapper.toDto(advanceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdvanceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdvanceDTO> findByCriteria(AdvanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Advance> specification = createSpecification(criteria);
        return advanceRepository.findAll(specification, page)
            .map(advanceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdvanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Advance> specification = createSpecification(criteria);
        return advanceRepository.count(specification);
    }

    /**
     * Function to convert AdvanceCriteria to a {@link Specification}
     */
    private Specification<Advance> createSpecification(AdvanceCriteria criteria) {
        Specification<Advance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Advance_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Advance_.amount));
            }
            if (criteria.getProvidedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProvidedOn(), Advance_.providedOn));
            }
            if (criteria.getMonthlyPayable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthlyPayable(), Advance_.monthlyPayable));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentStatus(), Advance_.paymentStatus));
            }
            if (criteria.getLeft() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeft(), Advance_.left));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Advance_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), Advance_.modifiedOn));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Advance_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
