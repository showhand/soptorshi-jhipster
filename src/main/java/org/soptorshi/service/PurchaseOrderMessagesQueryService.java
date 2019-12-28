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

import org.soptorshi.domain.PurchaseOrderMessages;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.PurchaseOrderMessagesRepository;
import org.soptorshi.repository.search.PurchaseOrderMessagesSearchRepository;
import org.soptorshi.service.dto.PurchaseOrderMessagesCriteria;
import org.soptorshi.service.dto.PurchaseOrderMessagesDTO;
import org.soptorshi.service.mapper.PurchaseOrderMessagesMapper;

/**
 * Service for executing complex queries for PurchaseOrderMessages entities in the database.
 * The main input is a {@link PurchaseOrderMessagesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseOrderMessagesDTO} or a {@link Page} of {@link PurchaseOrderMessagesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseOrderMessagesQueryService extends QueryService<PurchaseOrderMessages> {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderMessagesQueryService.class);

    private final PurchaseOrderMessagesRepository purchaseOrderMessagesRepository;

    private final PurchaseOrderMessagesMapper purchaseOrderMessagesMapper;

    private final PurchaseOrderMessagesSearchRepository purchaseOrderMessagesSearchRepository;

    public PurchaseOrderMessagesQueryService(PurchaseOrderMessagesRepository purchaseOrderMessagesRepository, PurchaseOrderMessagesMapper purchaseOrderMessagesMapper, PurchaseOrderMessagesSearchRepository purchaseOrderMessagesSearchRepository) {
        this.purchaseOrderMessagesRepository = purchaseOrderMessagesRepository;
        this.purchaseOrderMessagesMapper = purchaseOrderMessagesMapper;
        this.purchaseOrderMessagesSearchRepository = purchaseOrderMessagesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PurchaseOrderMessagesDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseOrderMessagesDTO> findByCriteria(PurchaseOrderMessagesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseOrderMessages> specification = createSpecification(criteria);
        return purchaseOrderMessagesMapper.toDto(purchaseOrderMessagesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurchaseOrderMessagesDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderMessagesDTO> findByCriteria(PurchaseOrderMessagesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseOrderMessages> specification = createSpecification(criteria);
        return purchaseOrderMessagesRepository.findAll(specification, page)
            .map(purchaseOrderMessagesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseOrderMessagesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseOrderMessages> specification = createSpecification(criteria);
        return purchaseOrderMessagesRepository.count(specification);
    }

    /**
     * Function to convert PurchaseOrderMessagesCriteria to a {@link Specification}
     */
    private Specification<PurchaseOrderMessages> createSpecification(PurchaseOrderMessagesCriteria criteria) {
        Specification<PurchaseOrderMessages> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PurchaseOrderMessages_.id));
            }
            if (criteria.getCommentedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommentedOn(), PurchaseOrderMessages_.commentedOn));
            }
            if (criteria.getCommenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommenterId(),
                    root -> root.join(PurchaseOrderMessages_.commenter, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPurchaseOrderId(),
                    root -> root.join(PurchaseOrderMessages_.purchaseOrder, JoinType.LEFT).get(PurchaseOrder_.id)));
            }
        }
        return specification;
    }
}
