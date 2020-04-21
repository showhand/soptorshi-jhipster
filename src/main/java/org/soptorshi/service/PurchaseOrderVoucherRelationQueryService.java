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

import org.soptorshi.domain.PurchaseOrderVoucherRelation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.PurchaseOrderVoucherRelationRepository;
import org.soptorshi.repository.search.PurchaseOrderVoucherRelationSearchRepository;
import org.soptorshi.service.dto.PurchaseOrderVoucherRelationCriteria;
import org.soptorshi.service.dto.PurchaseOrderVoucherRelationDTO;
import org.soptorshi.service.mapper.PurchaseOrderVoucherRelationMapper;

/**
 * Service for executing complex queries for PurchaseOrderVoucherRelation entities in the database.
 * The main input is a {@link PurchaseOrderVoucherRelationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseOrderVoucherRelationDTO} or a {@link Page} of {@link PurchaseOrderVoucherRelationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseOrderVoucherRelationQueryService extends QueryService<PurchaseOrderVoucherRelation> {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderVoucherRelationQueryService.class);

    private final PurchaseOrderVoucherRelationRepository purchaseOrderVoucherRelationRepository;

    private final PurchaseOrderVoucherRelationMapper purchaseOrderVoucherRelationMapper;

    private final PurchaseOrderVoucherRelationSearchRepository purchaseOrderVoucherRelationSearchRepository;

    public PurchaseOrderVoucherRelationQueryService(PurchaseOrderVoucherRelationRepository purchaseOrderVoucherRelationRepository, PurchaseOrderVoucherRelationMapper purchaseOrderVoucherRelationMapper, PurchaseOrderVoucherRelationSearchRepository purchaseOrderVoucherRelationSearchRepository) {
        this.purchaseOrderVoucherRelationRepository = purchaseOrderVoucherRelationRepository;
        this.purchaseOrderVoucherRelationMapper = purchaseOrderVoucherRelationMapper;
        this.purchaseOrderVoucherRelationSearchRepository = purchaseOrderVoucherRelationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PurchaseOrderVoucherRelationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseOrderVoucherRelationDTO> findByCriteria(PurchaseOrderVoucherRelationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseOrderVoucherRelation> specification = createSpecification(criteria);
        return purchaseOrderVoucherRelationMapper.toDto(purchaseOrderVoucherRelationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurchaseOrderVoucherRelationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderVoucherRelationDTO> findByCriteria(PurchaseOrderVoucherRelationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseOrderVoucherRelation> specification = createSpecification(criteria);
        return purchaseOrderVoucherRelationRepository.findAll(specification, page)
            .map(purchaseOrderVoucherRelationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseOrderVoucherRelationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseOrderVoucherRelation> specification = createSpecification(criteria);
        return purchaseOrderVoucherRelationRepository.count(specification);
    }

    /**
     * Function to convert PurchaseOrderVoucherRelationCriteria to a {@link Specification}
     */
    private Specification<PurchaseOrderVoucherRelation> createSpecification(PurchaseOrderVoucherRelationCriteria criteria) {
        Specification<PurchaseOrderVoucherRelation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PurchaseOrderVoucherRelation_.id));
            }
            if (criteria.getVoucherNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoucherNo(), PurchaseOrderVoucherRelation_.voucherNo));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), PurchaseOrderVoucherRelation_.amount));
            }
            if (criteria.getCreateBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreateBy(), PurchaseOrderVoucherRelation_.createBy));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), PurchaseOrderVoucherRelation_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), PurchaseOrderVoucherRelation_.modifiedOn));
            }
            if (criteria.getVoucherId() != null) {
                specification = specification.and(buildSpecification(criteria.getVoucherId(),
                    root -> root.join(PurchaseOrderVoucherRelation_.voucher, JoinType.LEFT).get(Voucher_.id)));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPurchaseOrderId(),
                    root -> root.join(PurchaseOrderVoucherRelation_.purchaseOrder, JoinType.LEFT).get(PurchaseOrder_.id)));
            }
        }
        return specification;
    }
}
