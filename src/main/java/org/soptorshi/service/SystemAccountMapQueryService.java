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

import org.soptorshi.domain.SystemAccountMap;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.SystemAccountMapRepository;
import org.soptorshi.repository.search.SystemAccountMapSearchRepository;
import org.soptorshi.service.dto.SystemAccountMapCriteria;
import org.soptorshi.service.dto.SystemAccountMapDTO;
import org.soptorshi.service.mapper.SystemAccountMapMapper;

/**
 * Service for executing complex queries for SystemAccountMap entities in the database.
 * The main input is a {@link SystemAccountMapCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SystemAccountMapDTO} or a {@link Page} of {@link SystemAccountMapDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemAccountMapQueryService extends QueryService<SystemAccountMap> {

    private final Logger log = LoggerFactory.getLogger(SystemAccountMapQueryService.class);

    private final SystemAccountMapRepository systemAccountMapRepository;

    private final SystemAccountMapMapper systemAccountMapMapper;

    private final SystemAccountMapSearchRepository systemAccountMapSearchRepository;

    public SystemAccountMapQueryService(SystemAccountMapRepository systemAccountMapRepository, SystemAccountMapMapper systemAccountMapMapper, SystemAccountMapSearchRepository systemAccountMapSearchRepository) {
        this.systemAccountMapRepository = systemAccountMapRepository;
        this.systemAccountMapMapper = systemAccountMapMapper;
        this.systemAccountMapSearchRepository = systemAccountMapSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SystemAccountMapDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SystemAccountMapDTO> findByCriteria(SystemAccountMapCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SystemAccountMap> specification = createSpecification(criteria);
        return systemAccountMapMapper.toDto(systemAccountMapRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SystemAccountMapDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemAccountMapDTO> findByCriteria(SystemAccountMapCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SystemAccountMap> specification = createSpecification(criteria);
        return systemAccountMapRepository.findAll(specification, page)
            .map(systemAccountMapMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemAccountMapCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SystemAccountMap> specification = createSpecification(criteria);
        return systemAccountMapRepository.count(specification);
    }

    /**
     * Function to convert SystemAccountMapCriteria to a {@link Specification}
     */
    private Specification<SystemAccountMap> createSpecification(SystemAccountMapCriteria criteria) {
        Specification<SystemAccountMap> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SystemAccountMap_.id));
            }
            if (criteria.getAccountType() != null) {
                specification = specification.and(buildSpecification(criteria.getAccountType(), SystemAccountMap_.accountType));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), SystemAccountMap_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), SystemAccountMap_.modifiedOn));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getAccountId(),
                    root -> root.join(SystemAccountMap_.account, JoinType.LEFT).get(MstAccount_.id)));
            }
        }
        return specification;
    }
}
