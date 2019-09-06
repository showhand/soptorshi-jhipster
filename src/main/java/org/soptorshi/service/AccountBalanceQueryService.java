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

import org.soptorshi.domain.AccountBalance;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.AccountBalanceRepository;
import org.soptorshi.repository.search.AccountBalanceSearchRepository;
import org.soptorshi.service.dto.AccountBalanceCriteria;
import org.soptorshi.service.dto.AccountBalanceDTO;
import org.soptorshi.service.mapper.AccountBalanceMapper;

/**
 * Service for executing complex queries for AccountBalance entities in the database.
 * The main input is a {@link AccountBalanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccountBalanceDTO} or a {@link Page} of {@link AccountBalanceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccountBalanceQueryService extends QueryService<AccountBalance> {

    private final Logger log = LoggerFactory.getLogger(AccountBalanceQueryService.class);

    private final AccountBalanceRepository accountBalanceRepository;

    private final AccountBalanceMapper accountBalanceMapper;

    private final AccountBalanceSearchRepository accountBalanceSearchRepository;

    public AccountBalanceQueryService(AccountBalanceRepository accountBalanceRepository, AccountBalanceMapper accountBalanceMapper, AccountBalanceSearchRepository accountBalanceSearchRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.accountBalanceMapper = accountBalanceMapper;
        this.accountBalanceSearchRepository = accountBalanceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AccountBalanceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccountBalanceDTO> findByCriteria(AccountBalanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AccountBalance> specification = createSpecification(criteria);
        return accountBalanceMapper.toDto(accountBalanceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccountBalanceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountBalanceDTO> findByCriteria(AccountBalanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AccountBalance> specification = createSpecification(criteria);
        return accountBalanceRepository.findAll(specification, page)
            .map(accountBalanceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccountBalanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AccountBalance> specification = createSpecification(criteria);
        return accountBalanceRepository.count(specification);
    }

    /**
     * Function to convert AccountBalanceCriteria to a {@link Specification}
     */
    private Specification<AccountBalance> createSpecification(AccountBalanceCriteria criteria) {
        Specification<AccountBalance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AccountBalance_.id));
            }
            if (criteria.getYearOpenBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearOpenBalance(), AccountBalance_.yearOpenBalance));
            }
            if (criteria.getYearOpenBalanceType() != null) {
                specification = specification.and(buildSpecification(criteria.getYearOpenBalanceType(), AccountBalance_.yearOpenBalanceType));
            }
            if (criteria.getTotDebitTrans() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotDebitTrans(), AccountBalance_.totDebitTrans));
            }
            if (criteria.getTotCreditTrans() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotCreditTrans(), AccountBalance_.totCreditTrans));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), AccountBalance_.modifiedOn));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), AccountBalance_.modifiedBy));
            }
            if (criteria.getFinancialAccountYearId() != null) {
                specification = specification.and(buildSpecification(criteria.getFinancialAccountYearId(),
                    root -> root.join(AccountBalance_.financialAccountYear, JoinType.LEFT).get(FinancialAccountYear_.id)));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getAccountId(),
                    root -> root.join(AccountBalance_.account, JoinType.LEFT).get(MstAccount_.id)));
            }
        }
        return specification;
    }
}
