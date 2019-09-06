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

import org.soptorshi.domain.MstGroup;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.MstGroupRepository;
import org.soptorshi.repository.search.MstGroupSearchRepository;
import org.soptorshi.service.dto.MstGroupCriteria;
import org.soptorshi.service.dto.MstGroupDTO;
import org.soptorshi.service.mapper.MstGroupMapper;

/**
 * Service for executing complex queries for MstGroup entities in the database.
 * The main input is a {@link MstGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MstGroupDTO} or a {@link Page} of {@link MstGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MstGroupQueryService extends QueryService<MstGroup> {

    private final Logger log = LoggerFactory.getLogger(MstGroupQueryService.class);

    private final MstGroupRepository mstGroupRepository;

    private final MstGroupMapper mstGroupMapper;

    private final MstGroupSearchRepository mstGroupSearchRepository;

    public MstGroupQueryService(MstGroupRepository mstGroupRepository, MstGroupMapper mstGroupMapper, MstGroupSearchRepository mstGroupSearchRepository) {
        this.mstGroupRepository = mstGroupRepository;
        this.mstGroupMapper = mstGroupMapper;
        this.mstGroupSearchRepository = mstGroupSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MstGroupDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MstGroupDTO> findByCriteria(MstGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MstGroup> specification = createSpecification(criteria);
        return mstGroupMapper.toDto(mstGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MstGroupDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MstGroupDTO> findByCriteria(MstGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MstGroup> specification = createSpecification(criteria);
        return mstGroupRepository.findAll(specification, page)
            .map(mstGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MstGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MstGroup> specification = createSpecification(criteria);
        return mstGroupRepository.count(specification);
    }

    /**
     * Function to convert MstGroupCriteria to a {@link Specification}
     */
    private Specification<MstGroup> createSpecification(MstGroupCriteria criteria) {
        Specification<MstGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MstGroup_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MstGroup_.name));
            }
            if (criteria.getMainGroup() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMainGroup(), MstGroup_.mainGroup));
            }
            if (criteria.getReservedFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getReservedFlag(), MstGroup_.reservedFlag));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), MstGroup_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), MstGroup_.modifiedOn));
            }
        }
        return specification;
    }
}
