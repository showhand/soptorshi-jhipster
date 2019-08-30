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

import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.QuotationRepository;
import org.soptorshi.repository.search.QuotationSearchRepository;
import org.soptorshi.service.dto.QuotationCriteria;
import org.soptorshi.service.dto.QuotationDTO;
import org.soptorshi.service.mapper.QuotationMapper;

/**
 * Service for executing complex queries for Quotation entities in the database.
 * The main input is a {@link QuotationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuotationDTO} or a {@link Page} of {@link QuotationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuotationQueryService extends QueryService<Quotation> {

    private final Logger log = LoggerFactory.getLogger(QuotationQueryService.class);

    private final QuotationRepository quotationRepository;

    private final QuotationMapper quotationMapper;

    private final QuotationSearchRepository quotationSearchRepository;

    public QuotationQueryService(QuotationRepository quotationRepository, QuotationMapper quotationMapper, QuotationSearchRepository quotationSearchRepository) {
        this.quotationRepository = quotationRepository;
        this.quotationMapper = quotationMapper;
        this.quotationSearchRepository = quotationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link QuotationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuotationDTO> findByCriteria(QuotationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Quotation> specification = createSpecification(criteria);
        return quotationMapper.toDto(quotationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuotationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuotationDTO> findByCriteria(QuotationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Quotation> specification = createSpecification(criteria);
        return quotationRepository.findAll(specification, page)
            .map(quotationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuotationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Quotation> specification = createSpecification(criteria);
        return quotationRepository.count(specification);
    }

    /**
     * Function to convert QuotationCriteria to a {@link Specification}
     */
    private Specification<Quotation> createSpecification(QuotationCriteria criteria) {
        Specification<Quotation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Quotation_.id));
            }
            if (criteria.getQuotationNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuotationNo(), Quotation_.quotationNo));
            }
            if (criteria.getSelectionStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getSelectionStatus(), Quotation_.selectionStatus));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), Quotation_.totalAmount));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Quotation_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), Quotation_.modifiedOn));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(Quotation_.requisition, JoinType.LEFT).get(Requisition_.id)));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(Quotation_.vendor, JoinType.LEFT).get(Vendor_.id)));
            }
        }
        return specification;
    }
}
