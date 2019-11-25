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

import org.soptorshi.domain.Salary;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.SalaryRepository;
import org.soptorshi.repository.search.SalarySearchRepository;
import org.soptorshi.service.dto.SalaryCriteria;
import org.soptorshi.service.dto.SalaryDTO;
import org.soptorshi.service.mapper.SalaryMapper;

/**
 * Service for executing complex queries for Salary entities in the database.
 * The main input is a {@link SalaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SalaryDTO} or a {@link Page} of {@link SalaryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalaryQueryService extends QueryService<Salary> {

    private final Logger log = LoggerFactory.getLogger(SalaryQueryService.class);

    private final SalaryRepository salaryRepository;

    private final SalaryMapper salaryMapper;

    private final SalarySearchRepository salarySearchRepository;

    public SalaryQueryService(SalaryRepository salaryRepository, SalaryMapper salaryMapper, SalarySearchRepository salarySearchRepository) {
        this.salaryRepository = salaryRepository;
        this.salaryMapper = salaryMapper;
        this.salarySearchRepository = salarySearchRepository;
    }

    /**
     * Return a {@link List} of {@link SalaryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SalaryDTO> findByCriteria(SalaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Salary> specification = createSpecification(criteria);
        return salaryMapper.toDto(salaryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SalaryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalaryDTO> findByCriteria(SalaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Salary> specification = createSpecification(criteria);
        return salaryRepository.findAll(specification, page)
            .map(salaryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Salary> specification = createSpecification(criteria);
        return salaryRepository.count(specification);
    }

    /**
     * Function to convert SalaryCriteria to a {@link Specification}
     */
    private Specification<Salary> createSpecification(SalaryCriteria criteria) {
        Specification<Salary> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Salary_.id));
            }
            if (criteria.getBasic() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasic(), Salary_.basic));
            }
            if (criteria.getGross() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGross(), Salary_.gross));
            }
            if (criteria.getStartedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartedOn(), Salary_.startedOn));
            }
            if (criteria.getEndedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndedOn(), Salary_.endedOn));
            }
            if (criteria.getSalaryStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getSalaryStatus(), Salary_.salaryStatus));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Salary_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), Salary_.modifiedOn));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Salary_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
