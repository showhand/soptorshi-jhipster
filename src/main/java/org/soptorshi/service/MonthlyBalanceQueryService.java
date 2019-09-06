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

import org.soptorshi.domain.MonthlyBalance;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.MonthlyBalanceRepository;
import org.soptorshi.repository.search.MonthlyBalanceSearchRepository;
import org.soptorshi.service.dto.MonthlyBalanceCriteria;
import org.soptorshi.service.dto.MonthlyBalanceDTO;
import org.soptorshi.service.mapper.MonthlyBalanceMapper;

/**
 * Service for executing complex queries for MonthlyBalance entities in the database.
 * The main input is a {@link MonthlyBalanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MonthlyBalanceDTO} or a {@link Page} of {@link MonthlyBalanceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MonthlyBalanceQueryService extends QueryService<MonthlyBalance> {

    private final Logger log = LoggerFactory.getLogger(MonthlyBalanceQueryService.class);

    private final MonthlyBalanceRepository monthlyBalanceRepository;

    private final MonthlyBalanceMapper monthlyBalanceMapper;

    private final MonthlyBalanceSearchRepository monthlyBalanceSearchRepository;

    public MonthlyBalanceQueryService(MonthlyBalanceRepository monthlyBalanceRepository, MonthlyBalanceMapper monthlyBalanceMapper, MonthlyBalanceSearchRepository monthlyBalanceSearchRepository) {
        this.monthlyBalanceRepository = monthlyBalanceRepository;
        this.monthlyBalanceMapper = monthlyBalanceMapper;
        this.monthlyBalanceSearchRepository = monthlyBalanceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MonthlyBalanceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MonthlyBalanceDTO> findByCriteria(MonthlyBalanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MonthlyBalance> specification = createSpecification(criteria);
        return monthlyBalanceMapper.toDto(monthlyBalanceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MonthlyBalanceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MonthlyBalanceDTO> findByCriteria(MonthlyBalanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MonthlyBalance> specification = createSpecification(criteria);
        return monthlyBalanceRepository.findAll(specification, page)
            .map(monthlyBalanceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MonthlyBalanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MonthlyBalance> specification = createSpecification(criteria);
        return monthlyBalanceRepository.count(specification);
    }

    /**
     * Function to convert MonthlyBalanceCriteria to a {@link Specification}
     */
    private Specification<MonthlyBalance> createSpecification(MonthlyBalanceCriteria criteria) {
        Specification<MonthlyBalance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MonthlyBalance_.id));
            }
            if (criteria.getMonthType() != null) {
                specification = specification.and(buildSpecification(criteria.getMonthType(), MonthlyBalance_.monthType));
            }
            if (criteria.getTotMonthDbBal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotMonthDbBal(), MonthlyBalance_.totMonthDbBal));
            }
            if (criteria.getTotMonthCrBal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotMonthCrBal(), MonthlyBalance_.totMonthCrBal));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), MonthlyBalance_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), MonthlyBalance_.modifiedOn));
            }
            if (criteria.getAccountBalanceId() != null) {
                specification = specification.and(buildSpecification(criteria.getAccountBalanceId(),
                    root -> root.join(MonthlyBalance_.accountBalance, JoinType.LEFT).get(AccountBalance_.id)));
            }
        }
        return specification;
    }
}
