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

import org.soptorshi.domain.MstAccount;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.search.MstAccountSearchRepository;
import org.soptorshi.service.dto.MstAccountCriteria;
import org.soptorshi.service.dto.MstAccountDTO;
import org.soptorshi.service.mapper.MstAccountMapper;

/**
 * Service for executing complex queries for MstAccount entities in the database.
 * The main input is a {@link MstAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MstAccountDTO} or a {@link Page} of {@link MstAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MstAccountQueryService extends QueryService<MstAccount> {

    private final Logger log = LoggerFactory.getLogger(MstAccountQueryService.class);

    private final MstAccountRepository mstAccountRepository;

    private final MstAccountMapper mstAccountMapper;

    private final MstAccountSearchRepository mstAccountSearchRepository;

    public MstAccountQueryService(MstAccountRepository mstAccountRepository, MstAccountMapper mstAccountMapper, MstAccountSearchRepository mstAccountSearchRepository) {
        this.mstAccountRepository = mstAccountRepository;
        this.mstAccountMapper = mstAccountMapper;
        this.mstAccountSearchRepository = mstAccountSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MstAccountDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MstAccountDTO> findByCriteria(MstAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MstAccount> specification = createSpecification(criteria);
        return mstAccountMapper.toDto(mstAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MstAccountDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MstAccountDTO> findByCriteria(MstAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MstAccount> specification = createSpecification(criteria);
        return mstAccountRepository.findAll(specification, page)
            .map(mstAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MstAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MstAccount> specification = createSpecification(criteria);
        return mstAccountRepository.count(specification);
    }

    /**
     * Function to convert MstAccountCriteria to a {@link Specification}
     */
    private Specification<MstAccount> createSpecification(MstAccountCriteria criteria) {
        Specification<MstAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MstAccount_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), MstAccount_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MstAccount_.name));
            }
            if (criteria.getYearOpenBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearOpenBalance(), MstAccount_.yearOpenBalance));
            }
            if (criteria.getYearOpenBalanceType() != null) {
                specification = specification.and(buildSpecification(criteria.getYearOpenBalanceType(), MstAccount_.yearOpenBalanceType));
            }
            if (criteria.getYearCloseBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearCloseBalance(), MstAccount_.yearCloseBalance));
            }
            if (criteria.getReservedFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getReservedFlag(), MstAccount_.reservedFlag));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), MstAccount_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), MstAccount_.modifiedOn));
            }
            if (criteria.getDepreciationRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepreciationRate(), MstAccount_.depreciationRate));
            }
            if (criteria.getDepreciationType() != null) {
                specification = specification.and(buildSpecification(criteria.getDepreciationType(), MstAccount_.depreciationType));
            }
            if (criteria.getGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupId(),
                    root -> root.join(MstAccount_.group, JoinType.LEFT).get(MstGroup_.id)));
            }
        }
        return specification;
    }
}
