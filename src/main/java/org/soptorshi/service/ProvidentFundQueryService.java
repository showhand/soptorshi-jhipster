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

import org.soptorshi.domain.ProvidentFund;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ProvidentFundRepository;
import org.soptorshi.repository.search.ProvidentFundSearchRepository;
import org.soptorshi.service.dto.ProvidentFundCriteria;
import org.soptorshi.service.dto.ProvidentFundDTO;
import org.soptorshi.service.mapper.ProvidentFundMapper;

/**
 * Service for executing complex queries for ProvidentFund entities in the database.
 * The main input is a {@link ProvidentFundCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProvidentFundDTO} or a {@link Page} of {@link ProvidentFundDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProvidentFundQueryService extends QueryService<ProvidentFund> {

    private final Logger log = LoggerFactory.getLogger(ProvidentFundQueryService.class);

    private final ProvidentFundRepository providentFundRepository;

    private final ProvidentFundMapper providentFundMapper;

    private final ProvidentFundSearchRepository providentFundSearchRepository;

    public ProvidentFundQueryService(ProvidentFundRepository providentFundRepository, ProvidentFundMapper providentFundMapper, ProvidentFundSearchRepository providentFundSearchRepository) {
        this.providentFundRepository = providentFundRepository;
        this.providentFundMapper = providentFundMapper;
        this.providentFundSearchRepository = providentFundSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProvidentFundDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProvidentFundDTO> findByCriteria(ProvidentFundCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProvidentFund> specification = createSpecification(criteria);
        return providentFundMapper.toDto(providentFundRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProvidentFundDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProvidentFundDTO> findByCriteria(ProvidentFundCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProvidentFund> specification = createSpecification(criteria);
        return providentFundRepository.findAll(specification, page)
            .map(providentFundMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProvidentFundCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProvidentFund> specification = createSpecification(criteria);
        return providentFundRepository.count(specification);
    }

    /**
     * Function to convert ProvidentFundCriteria to a {@link Specification}
     */
    private Specification<ProvidentFund> createSpecification(ProvidentFundCriteria criteria) {
        Specification<ProvidentFund> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProvidentFund_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ProvidentFund_.startDate));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), ProvidentFund_.rate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ProvidentFund_.status));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ProvidentFund_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), ProvidentFund_.modifiedOn));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(ProvidentFund_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
