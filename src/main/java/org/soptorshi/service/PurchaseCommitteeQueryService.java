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

import org.soptorshi.domain.PurchaseCommittee;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.PurchaseCommitteeRepository;
import org.soptorshi.repository.search.PurchaseCommitteeSearchRepository;
import org.soptorshi.service.dto.PurchaseCommitteeCriteria;
import org.soptorshi.service.dto.PurchaseCommitteeDTO;
import org.soptorshi.service.mapper.PurchaseCommitteeMapper;

/**
 * Service for executing complex queries for PurchaseCommittee entities in the database.
 * The main input is a {@link PurchaseCommitteeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseCommitteeDTO} or a {@link Page} of {@link PurchaseCommitteeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseCommitteeQueryService extends QueryService<PurchaseCommittee> {

    private final Logger log = LoggerFactory.getLogger(PurchaseCommitteeQueryService.class);

    private final PurchaseCommitteeRepository purchaseCommitteeRepository;

    private final PurchaseCommitteeMapper purchaseCommitteeMapper;

    private final PurchaseCommitteeSearchRepository purchaseCommitteeSearchRepository;

    public PurchaseCommitteeQueryService(PurchaseCommitteeRepository purchaseCommitteeRepository, PurchaseCommitteeMapper purchaseCommitteeMapper, PurchaseCommitteeSearchRepository purchaseCommitteeSearchRepository) {
        this.purchaseCommitteeRepository = purchaseCommitteeRepository;
        this.purchaseCommitteeMapper = purchaseCommitteeMapper;
        this.purchaseCommitteeSearchRepository = purchaseCommitteeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PurchaseCommitteeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseCommitteeDTO> findByCriteria(PurchaseCommitteeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseCommittee> specification = createSpecification(criteria);
        return purchaseCommitteeMapper.toDto(purchaseCommitteeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurchaseCommitteeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseCommitteeDTO> findByCriteria(PurchaseCommitteeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseCommittee> specification = createSpecification(criteria);
        return purchaseCommitteeRepository.findAll(specification, page)
            .map(purchaseCommitteeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseCommitteeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseCommittee> specification = createSpecification(criteria);
        return purchaseCommitteeRepository.count(specification);
    }

    /**
     * Function to convert PurchaseCommitteeCriteria to a {@link Specification}
     */
    private Specification<PurchaseCommittee> createSpecification(PurchaseCommitteeCriteria criteria) {
        Specification<PurchaseCommittee> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PurchaseCommittee_.id));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(PurchaseCommittee_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
