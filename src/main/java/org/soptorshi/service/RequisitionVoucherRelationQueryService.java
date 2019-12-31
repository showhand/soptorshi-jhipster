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

import org.soptorshi.domain.RequisitionVoucherRelation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.RequisitionVoucherRelationRepository;
import org.soptorshi.repository.search.RequisitionVoucherRelationSearchRepository;
import org.soptorshi.service.dto.RequisitionVoucherRelationCriteria;
import org.soptorshi.service.dto.RequisitionVoucherRelationDTO;
import org.soptorshi.service.mapper.RequisitionVoucherRelationMapper;

/**
 * Service for executing complex queries for RequisitionVoucherRelation entities in the database.
 * The main input is a {@link RequisitionVoucherRelationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RequisitionVoucherRelationDTO} or a {@link Page} of {@link RequisitionVoucherRelationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RequisitionVoucherRelationQueryService extends QueryService<RequisitionVoucherRelation> {

    private final Logger log = LoggerFactory.getLogger(RequisitionVoucherRelationQueryService.class);

    private final RequisitionVoucherRelationRepository requisitionVoucherRelationRepository;

    private final RequisitionVoucherRelationMapper requisitionVoucherRelationMapper;

    private final RequisitionVoucherRelationSearchRepository requisitionVoucherRelationSearchRepository;

    public RequisitionVoucherRelationQueryService(RequisitionVoucherRelationRepository requisitionVoucherRelationRepository, RequisitionVoucherRelationMapper requisitionVoucherRelationMapper, RequisitionVoucherRelationSearchRepository requisitionVoucherRelationSearchRepository) {
        this.requisitionVoucherRelationRepository = requisitionVoucherRelationRepository;
        this.requisitionVoucherRelationMapper = requisitionVoucherRelationMapper;
        this.requisitionVoucherRelationSearchRepository = requisitionVoucherRelationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RequisitionVoucherRelationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RequisitionVoucherRelationDTO> findByCriteria(RequisitionVoucherRelationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RequisitionVoucherRelation> specification = createSpecification(criteria);
        return requisitionVoucherRelationMapper.toDto(requisitionVoucherRelationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RequisitionVoucherRelationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RequisitionVoucherRelationDTO> findByCriteria(RequisitionVoucherRelationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RequisitionVoucherRelation> specification = createSpecification(criteria);
        return requisitionVoucherRelationRepository.findAll(specification, page)
            .map(requisitionVoucherRelationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RequisitionVoucherRelationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RequisitionVoucherRelation> specification = createSpecification(criteria);
        return requisitionVoucherRelationRepository.count(specification);
    }

    /**
     * Function to convert RequisitionVoucherRelationCriteria to a {@link Specification}
     */
    private Specification<RequisitionVoucherRelation> createSpecification(RequisitionVoucherRelationCriteria criteria) {
        Specification<RequisitionVoucherRelation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RequisitionVoucherRelation_.id));
            }
            if (criteria.getVoucherNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoucherNo(), RequisitionVoucherRelation_.voucherNo));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), RequisitionVoucherRelation_.amount));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), RequisitionVoucherRelation_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), RequisitionVoucherRelation_.modifiedOn));
            }
            if (criteria.getVoucherId() != null) {
                specification = specification.and(buildSpecification(criteria.getVoucherId(),
                    root -> root.join(RequisitionVoucherRelation_.voucher, JoinType.LEFT).get(Voucher_.id)));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(RequisitionVoucherRelation_.requisition, JoinType.LEFT).get(Requisition_.id)));
            }
        }
        return specification;
    }
}
