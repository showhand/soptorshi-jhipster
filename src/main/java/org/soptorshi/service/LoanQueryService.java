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

import org.soptorshi.domain.Loan;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.LoanRepository;
import org.soptorshi.repository.search.LoanSearchRepository;
import org.soptorshi.service.dto.LoanCriteria;
import org.soptorshi.service.dto.LoanDTO;
import org.soptorshi.service.mapper.LoanMapper;

/**
 * Service for executing complex queries for Loan entities in the database.
 * The main input is a {@link LoanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanDTO} or a {@link Page} of {@link LoanDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanQueryService extends QueryService<Loan> {

    private final Logger log = LoggerFactory.getLogger(LoanQueryService.class);

    private final LoanRepository loanRepository;

    private final LoanMapper loanMapper;

    private final LoanSearchRepository loanSearchRepository;

    public LoanQueryService(LoanRepository loanRepository, LoanMapper loanMapper, LoanSearchRepository loanSearchRepository) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.loanSearchRepository = loanSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LoanDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanDTO> findByCriteria(LoanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Loan> specification = createSpecification(criteria);
        return loanMapper.toDto(loanRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanDTO> findByCriteria(LoanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Loan> specification = createSpecification(criteria);
        return loanRepository.findAll(specification, page)
            .map(loanMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Loan> specification = createSpecification(criteria);
        return loanRepository.count(specification);
    }

    /**
     * Function to convert LoanCriteria to a {@link Specification}
     */
    private Specification<Loan> createSpecification(LoanCriteria criteria) {
        Specification<Loan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Loan_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Loan_.amount));
            }
            if (criteria.getTakenOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTakenOn(), Loan_.takenOn));
            }
            if (criteria.getMonthlyPayable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthlyPayable(), Loan_.monthlyPayable));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentStatus(), Loan_.paymentStatus));
            }
            if (criteria.getLeft() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeft(), Loan_.left));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Loan_.modifiedBy));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), Loan_.modifiedDate));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Loan_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
