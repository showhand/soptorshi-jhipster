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

import org.soptorshi.domain.SalaryMessages;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.SalaryMessagesRepository;
import org.soptorshi.repository.search.SalaryMessagesSearchRepository;
import org.soptorshi.service.dto.SalaryMessagesCriteria;
import org.soptorshi.service.dto.SalaryMessagesDTO;
import org.soptorshi.service.mapper.SalaryMessagesMapper;

/**
 * Service for executing complex queries for SalaryMessages entities in the database.
 * The main input is a {@link SalaryMessagesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SalaryMessagesDTO} or a {@link Page} of {@link SalaryMessagesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalaryMessagesQueryService extends QueryService<SalaryMessages> {

    private final Logger log = LoggerFactory.getLogger(SalaryMessagesQueryService.class);

    private final SalaryMessagesRepository salaryMessagesRepository;

    private final SalaryMessagesMapper salaryMessagesMapper;

    private final SalaryMessagesSearchRepository salaryMessagesSearchRepository;

    public SalaryMessagesQueryService(SalaryMessagesRepository salaryMessagesRepository, SalaryMessagesMapper salaryMessagesMapper, SalaryMessagesSearchRepository salaryMessagesSearchRepository) {
        this.salaryMessagesRepository = salaryMessagesRepository;
        this.salaryMessagesMapper = salaryMessagesMapper;
        this.salaryMessagesSearchRepository = salaryMessagesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SalaryMessagesDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SalaryMessagesDTO> findByCriteria(SalaryMessagesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SalaryMessages> specification = createSpecification(criteria);
        return salaryMessagesMapper.toDto(salaryMessagesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SalaryMessagesDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalaryMessagesDTO> findByCriteria(SalaryMessagesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalaryMessages> specification = createSpecification(criteria);
        return salaryMessagesRepository.findAll(specification, page)
            .map(salaryMessagesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalaryMessagesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SalaryMessages> specification = createSpecification(criteria);
        return salaryMessagesRepository.count(specification);
    }

    /**
     * Function to convert SalaryMessagesCriteria to a {@link Specification}
     */
    private Specification<SalaryMessages> createSpecification(SalaryMessagesCriteria criteria) {
        Specification<SalaryMessages> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SalaryMessages_.id));
            }
            if (criteria.getCommentedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommentedOn(), SalaryMessages_.commentedOn));
            }
            if (criteria.getCommenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommenterId(),
                    root -> root.join(SalaryMessages_.commenter, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getMonthlySalaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getMonthlySalaryId(),
                    root -> root.join(SalaryMessages_.monthlySalary, JoinType.LEFT).get(MonthlySalary_.id)));
            }
        }
        return specification;
    }
}
