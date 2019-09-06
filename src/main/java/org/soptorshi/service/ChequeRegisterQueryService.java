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

import org.soptorshi.domain.ChequeRegister;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ChequeRegisterRepository;
import org.soptorshi.repository.search.ChequeRegisterSearchRepository;
import org.soptorshi.service.dto.ChequeRegisterCriteria;
import org.soptorshi.service.dto.ChequeRegisterDTO;
import org.soptorshi.service.mapper.ChequeRegisterMapper;

/**
 * Service for executing complex queries for ChequeRegister entities in the database.
 * The main input is a {@link ChequeRegisterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChequeRegisterDTO} or a {@link Page} of {@link ChequeRegisterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChequeRegisterQueryService extends QueryService<ChequeRegister> {

    private final Logger log = LoggerFactory.getLogger(ChequeRegisterQueryService.class);

    private final ChequeRegisterRepository chequeRegisterRepository;

    private final ChequeRegisterMapper chequeRegisterMapper;

    private final ChequeRegisterSearchRepository chequeRegisterSearchRepository;

    public ChequeRegisterQueryService(ChequeRegisterRepository chequeRegisterRepository, ChequeRegisterMapper chequeRegisterMapper, ChequeRegisterSearchRepository chequeRegisterSearchRepository) {
        this.chequeRegisterRepository = chequeRegisterRepository;
        this.chequeRegisterMapper = chequeRegisterMapper;
        this.chequeRegisterSearchRepository = chequeRegisterSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ChequeRegisterDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChequeRegisterDTO> findByCriteria(ChequeRegisterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ChequeRegister> specification = createSpecification(criteria);
        return chequeRegisterMapper.toDto(chequeRegisterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ChequeRegisterDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChequeRegisterDTO> findByCriteria(ChequeRegisterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ChequeRegister> specification = createSpecification(criteria);
        return chequeRegisterRepository.findAll(specification, page)
            .map(chequeRegisterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChequeRegisterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ChequeRegister> specification = createSpecification(criteria);
        return chequeRegisterRepository.count(specification);
    }

    /**
     * Function to convert ChequeRegisterCriteria to a {@link Specification}
     */
    private Specification<ChequeRegister> createSpecification(ChequeRegisterCriteria criteria) {
        Specification<ChequeRegister> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ChequeRegister_.id));
            }
            if (criteria.getChequeNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChequeNo(), ChequeRegister_.chequeNo));
            }
            if (criteria.getChequeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChequeDate(), ChequeRegister_.chequeDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ChequeRegister_.status));
            }
            if (criteria.getRealizationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRealizationDate(), ChequeRegister_.realizationDate));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ChequeRegister_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), ChequeRegister_.modifiedOn));
            }
        }
        return specification;
    }
}
