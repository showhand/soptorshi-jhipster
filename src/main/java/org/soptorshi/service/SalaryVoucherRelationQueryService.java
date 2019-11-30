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

import org.soptorshi.domain.SalaryVoucherRelation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.SalaryVoucherRelationRepository;
import org.soptorshi.repository.search.SalaryVoucherRelationSearchRepository;
import org.soptorshi.service.dto.SalaryVoucherRelationCriteria;
import org.soptorshi.service.dto.SalaryVoucherRelationDTO;
import org.soptorshi.service.mapper.SalaryVoucherRelationMapper;

/**
 * Service for executing complex queries for SalaryVoucherRelation entities in the database.
 * The main input is a {@link SalaryVoucherRelationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SalaryVoucherRelationDTO} or a {@link Page} of {@link SalaryVoucherRelationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalaryVoucherRelationQueryService extends QueryService<SalaryVoucherRelation> {

    private final Logger log = LoggerFactory.getLogger(SalaryVoucherRelationQueryService.class);

    private final SalaryVoucherRelationRepository salaryVoucherRelationRepository;

    private final SalaryVoucherRelationMapper salaryVoucherRelationMapper;

    private final SalaryVoucherRelationSearchRepository salaryVoucherRelationSearchRepository;

    public SalaryVoucherRelationQueryService(SalaryVoucherRelationRepository salaryVoucherRelationRepository, SalaryVoucherRelationMapper salaryVoucherRelationMapper, SalaryVoucherRelationSearchRepository salaryVoucherRelationSearchRepository) {
        this.salaryVoucherRelationRepository = salaryVoucherRelationRepository;
        this.salaryVoucherRelationMapper = salaryVoucherRelationMapper;
        this.salaryVoucherRelationSearchRepository = salaryVoucherRelationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SalaryVoucherRelationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SalaryVoucherRelationDTO> findByCriteria(SalaryVoucherRelationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SalaryVoucherRelation> specification = createSpecification(criteria);
        return salaryVoucherRelationMapper.toDto(salaryVoucherRelationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SalaryVoucherRelationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalaryVoucherRelationDTO> findByCriteria(SalaryVoucherRelationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalaryVoucherRelation> specification = createSpecification(criteria);
        return salaryVoucherRelationRepository.findAll(specification, page)
            .map(salaryVoucherRelationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalaryVoucherRelationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SalaryVoucherRelation> specification = createSpecification(criteria);
        return salaryVoucherRelationRepository.count(specification);
    }

    /**
     * Function to convert SalaryVoucherRelationCriteria to a {@link Specification}
     */
    private Specification<SalaryVoucherRelation> createSpecification(SalaryVoucherRelationCriteria criteria) {
        Specification<SalaryVoucherRelation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SalaryVoucherRelation_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), SalaryVoucherRelation_.year));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), SalaryVoucherRelation_.month));
            }
            if (criteria.getVoucherNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoucherNo(), SalaryVoucherRelation_.voucherNo));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), SalaryVoucherRelation_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), SalaryVoucherRelation_.modifiedOn));
            }
            if (criteria.getOfficeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfficeId(),
                    root -> root.join(SalaryVoucherRelation_.office, JoinType.LEFT).get(Office_.id)));
            }
        }
        return specification;
    }
}
