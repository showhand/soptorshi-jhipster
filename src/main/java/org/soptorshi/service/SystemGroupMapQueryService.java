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

import org.soptorshi.domain.SystemGroupMap;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.SystemGroupMapRepository;
import org.soptorshi.repository.search.SystemGroupMapSearchRepository;
import org.soptorshi.service.dto.SystemGroupMapCriteria;
import org.soptorshi.service.dto.SystemGroupMapDTO;
import org.soptorshi.service.mapper.SystemGroupMapMapper;

/**
 * Service for executing complex queries for SystemGroupMap entities in the database.
 * The main input is a {@link SystemGroupMapCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SystemGroupMapDTO} or a {@link Page} of {@link SystemGroupMapDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SystemGroupMapQueryService extends QueryService<SystemGroupMap> {

    private final Logger log = LoggerFactory.getLogger(SystemGroupMapQueryService.class);

    private final SystemGroupMapRepository systemGroupMapRepository;

    private final SystemGroupMapMapper systemGroupMapMapper;

    private final SystemGroupMapSearchRepository systemGroupMapSearchRepository;

    public SystemGroupMapQueryService(SystemGroupMapRepository systemGroupMapRepository, SystemGroupMapMapper systemGroupMapMapper, SystemGroupMapSearchRepository systemGroupMapSearchRepository) {
        this.systemGroupMapRepository = systemGroupMapRepository;
        this.systemGroupMapMapper = systemGroupMapMapper;
        this.systemGroupMapSearchRepository = systemGroupMapSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SystemGroupMapDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SystemGroupMapDTO> findByCriteria(SystemGroupMapCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SystemGroupMap> specification = createSpecification(criteria);
        return systemGroupMapMapper.toDto(systemGroupMapRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SystemGroupMapDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SystemGroupMapDTO> findByCriteria(SystemGroupMapCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SystemGroupMap> specification = createSpecification(criteria);
        return systemGroupMapRepository.findAll(specification, page)
            .map(systemGroupMapMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SystemGroupMapCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SystemGroupMap> specification = createSpecification(criteria);
        return systemGroupMapRepository.count(specification);
    }

    /**
     * Function to convert SystemGroupMapCriteria to a {@link Specification}
     */
    private Specification<SystemGroupMap> createSpecification(SystemGroupMapCriteria criteria) {
        Specification<SystemGroupMap> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SystemGroupMap_.id));
            }
            if (criteria.getGroupType() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupType(), SystemGroupMap_.groupType));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), SystemGroupMap_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), SystemGroupMap_.modifiedOn));
            }
            if (criteria.getGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupId(),
                    root -> root.join(SystemGroupMap_.group, JoinType.LEFT).get(MstGroup_.id)));
            }
        }
        return specification;
    }
}
