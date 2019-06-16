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

import org.soptorshi.domain.DesignationWiseAllowance;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.DesignationWiseAllowanceRepository;
import org.soptorshi.repository.search.DesignationWiseAllowanceSearchRepository;
import org.soptorshi.service.dto.DesignationWiseAllowanceCriteria;
import org.soptorshi.service.dto.DesignationWiseAllowanceDTO;
import org.soptorshi.service.mapper.DesignationWiseAllowanceMapper;

/**
 * Service for executing complex queries for DesignationWiseAllowance entities in the database.
 * The main input is a {@link DesignationWiseAllowanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DesignationWiseAllowanceDTO} or a {@link Page} of {@link DesignationWiseAllowanceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DesignationWiseAllowanceQueryService extends QueryService<DesignationWiseAllowance> {

    private final Logger log = LoggerFactory.getLogger(DesignationWiseAllowanceQueryService.class);

    private final DesignationWiseAllowanceRepository designationWiseAllowanceRepository;

    private final DesignationWiseAllowanceMapper designationWiseAllowanceMapper;

    private final DesignationWiseAllowanceSearchRepository designationWiseAllowanceSearchRepository;

    public DesignationWiseAllowanceQueryService(DesignationWiseAllowanceRepository designationWiseAllowanceRepository, DesignationWiseAllowanceMapper designationWiseAllowanceMapper, DesignationWiseAllowanceSearchRepository designationWiseAllowanceSearchRepository) {
        this.designationWiseAllowanceRepository = designationWiseAllowanceRepository;
        this.designationWiseAllowanceMapper = designationWiseAllowanceMapper;
        this.designationWiseAllowanceSearchRepository = designationWiseAllowanceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DesignationWiseAllowanceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DesignationWiseAllowanceDTO> findByCriteria(DesignationWiseAllowanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DesignationWiseAllowance> specification = createSpecification(criteria);
        return designationWiseAllowanceMapper.toDto(designationWiseAllowanceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DesignationWiseAllowanceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DesignationWiseAllowanceDTO> findByCriteria(DesignationWiseAllowanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DesignationWiseAllowance> specification = createSpecification(criteria);
        return designationWiseAllowanceRepository.findAll(specification, page)
            .map(designationWiseAllowanceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DesignationWiseAllowanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DesignationWiseAllowance> specification = createSpecification(criteria);
        return designationWiseAllowanceRepository.count(specification);
    }

    /**
     * Function to convert DesignationWiseAllowanceCriteria to a {@link Specification}
     */
    private Specification<DesignationWiseAllowance> createSpecification(DesignationWiseAllowanceCriteria criteria) {
        Specification<DesignationWiseAllowance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DesignationWiseAllowance_.id));
            }
            if (criteria.getAllowanceType() != null) {
                specification = specification.and(buildSpecification(criteria.getAllowanceType(), DesignationWiseAllowance_.allowanceType));
            }
            if (criteria.getAllowanceCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getAllowanceCategory(), DesignationWiseAllowance_.allowanceCategory));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), DesignationWiseAllowance_.amount));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), DesignationWiseAllowance_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), DesignationWiseAllowance_.modifiedOn));
            }
            if (criteria.getDesignationId() != null) {
                specification = specification.and(buildSpecification(criteria.getDesignationId(),
                    root -> root.join(DesignationWiseAllowance_.designation, JoinType.LEFT).get(Designation_.id)));
            }
        }
        return specification;
    }
}
