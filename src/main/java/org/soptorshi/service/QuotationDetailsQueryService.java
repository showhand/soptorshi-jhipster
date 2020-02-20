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

import org.soptorshi.domain.QuotationDetails;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.QuotationDetailsRepository;
import org.soptorshi.repository.search.QuotationDetailsSearchRepository;
import org.soptorshi.service.dto.QuotationDetailsCriteria;
import org.soptorshi.service.dto.QuotationDetailsDTO;
import org.soptorshi.service.mapper.QuotationDetailsMapper;

/**
 * Service for executing complex queries for QuotationDetails entities in the database.
 * The main input is a {@link QuotationDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuotationDetailsDTO} or a {@link Page} of {@link QuotationDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuotationDetailsQueryService extends QueryService<QuotationDetails> {

    private final Logger log = LoggerFactory.getLogger(QuotationDetailsQueryService.class);

    private final QuotationDetailsRepository quotationDetailsRepository;

    private final QuotationDetailsMapper quotationDetailsMapper;

    private final QuotationDetailsSearchRepository quotationDetailsSearchRepository;

    public QuotationDetailsQueryService(QuotationDetailsRepository quotationDetailsRepository, QuotationDetailsMapper quotationDetailsMapper, QuotationDetailsSearchRepository quotationDetailsSearchRepository) {
        this.quotationDetailsRepository = quotationDetailsRepository;
        this.quotationDetailsMapper = quotationDetailsMapper;
        this.quotationDetailsSearchRepository = quotationDetailsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link QuotationDetailsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuotationDetailsDTO> findByCriteria(QuotationDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuotationDetails> specification = createSpecification(criteria);
        return quotationDetailsMapper.toDto(quotationDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuotationDetailsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuotationDetailsDTO> findByCriteria(QuotationDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuotationDetails> specification = createSpecification(criteria);
        return quotationDetailsRepository.findAll(specification, page)
            .map(quotationDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuotationDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuotationDetails> specification = createSpecification(criteria);
        return quotationDetailsRepository.count(specification);
    }

    /**
     * Function to convert QuotationDetailsCriteria to a {@link Specification}
     */
    private Specification<QuotationDetails> createSpecification(QuotationDetailsCriteria criteria) {
        Specification<QuotationDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), QuotationDetails_.id));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrency(), QuotationDetails_.currency));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), QuotationDetails_.rate));
            }
            if (criteria.getUnitOfMeasurements() != null) {
                specification = specification.and(buildSpecification(criteria.getUnitOfMeasurements(), QuotationDetails_.unitOfMeasurements));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), QuotationDetails_.quantity));
            }
            if (criteria.getPayType() != null) {
                specification = specification.and(buildSpecification(criteria.getPayType(), QuotationDetails_.payType));
            }
            if (criteria.getCreditLimit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreditLimit(), QuotationDetails_.creditLimit));
            }
            if (criteria.getVatStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getVatStatus(), QuotationDetails_.vatStatus));
            }
            if (criteria.getVatPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVatPercentage(), QuotationDetails_.vatPercentage));
            }
            if (criteria.getAitStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getAitStatus(), QuotationDetails_.aitStatus));
            }
            if (criteria.getAitPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAitPercentage(), QuotationDetails_.aitPercentage));
            }
            if (criteria.getEstimatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedDate(), QuotationDetails_.estimatedDate));
            }
            if (criteria.getWarrantyStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getWarrantyStatus(), QuotationDetails_.warrantyStatus));
            }
            if (criteria.getLoadingPort() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoadingPort(), QuotationDetails_.loadingPort));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), QuotationDetails_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), QuotationDetails_.modifiedOn));
            }
            if (criteria.getQuotationId() != null) {
                specification = specification.and(buildSpecification(criteria.getQuotationId(),
                    root -> root.join(QuotationDetails_.quotation, JoinType.LEFT).get(Quotation_.id)));
            }
            if (criteria.getRequisitionDetailsId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionDetailsId(),
                    root -> root.join(QuotationDetails_.requisitionDetails, JoinType.LEFT).get(RequisitionDetails_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(QuotationDetails_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
