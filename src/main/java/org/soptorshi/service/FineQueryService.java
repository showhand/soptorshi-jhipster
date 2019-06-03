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

import org.soptorshi.domain.Fine;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.FineRepository;
import org.soptorshi.repository.search.FineSearchRepository;
import org.soptorshi.service.dto.FineCriteria;
import org.soptorshi.service.dto.FineDTO;
import org.soptorshi.service.mapper.FineMapper;

/**
 * Service for executing complex queries for Fine entities in the database.
 * The main input is a {@link FineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FineDTO} or a {@link Page} of {@link FineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FineQueryService extends QueryService<Fine> {

    private final Logger log = LoggerFactory.getLogger(FineQueryService.class);

    private final FineRepository fineRepository;

    private final FineMapper fineMapper;

    private final FineSearchRepository fineSearchRepository;

    public FineQueryService(FineRepository fineRepository, FineMapper fineMapper, FineSearchRepository fineSearchRepository) {
        this.fineRepository = fineRepository;
        this.fineMapper = fineMapper;
        this.fineSearchRepository = fineSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FineDTO> findByCriteria(FineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fine> specification = createSpecification(criteria);
        return fineMapper.toDto(fineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FineDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FineDTO> findByCriteria(FineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fine> specification = createSpecification(criteria);
        return fineRepository.findAll(specification, page)
            .map(fineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fine> specification = createSpecification(criteria);
        return fineRepository.count(specification);
    }

    /**
     * Function to convert FineCriteria to a {@link Specification}
     */
    private Specification<Fine> createSpecification(FineCriteria criteria) {
        Specification<Fine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Fine_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Fine_.amount));
            }
            if (criteria.getFineDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFineDate(), Fine_.fineDate));
            }
            if (criteria.getMonthlyPayable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthlyPayable(), Fine_.monthlyPayable));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentStatus(), Fine_.paymentStatus));
            }
            if (criteria.getLeft() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeft(), Fine_.left));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Fine_.modifiedBy));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), Fine_.modifiedDate));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Fine_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
