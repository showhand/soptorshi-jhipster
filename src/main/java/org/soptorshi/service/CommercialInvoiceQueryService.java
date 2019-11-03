package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.CommercialInvoiceRepository;
import org.soptorshi.repository.search.CommercialInvoiceSearchRepository;
import org.soptorshi.service.dto.CommercialInvoiceCriteria;
import org.soptorshi.service.dto.CommercialInvoiceDTO;
import org.soptorshi.service.mapper.CommercialInvoiceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for CommercialInvoice entities in the database.
 * The main input is a {@link CommercialInvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialInvoiceDTO} or a {@link Page} of {@link CommercialInvoiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialInvoiceQueryService extends QueryService<CommercialInvoice> {

    private final Logger log = LoggerFactory.getLogger(CommercialInvoiceQueryService.class);

    private final CommercialInvoiceRepository commercialInvoiceRepository;

    private final CommercialInvoiceMapper commercialInvoiceMapper;

    private final CommercialInvoiceSearchRepository commercialInvoiceSearchRepository;

    public CommercialInvoiceQueryService(CommercialInvoiceRepository commercialInvoiceRepository, CommercialInvoiceMapper commercialInvoiceMapper, CommercialInvoiceSearchRepository commercialInvoiceSearchRepository) {
        this.commercialInvoiceRepository = commercialInvoiceRepository;
        this.commercialInvoiceMapper = commercialInvoiceMapper;
        this.commercialInvoiceSearchRepository = commercialInvoiceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialInvoiceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialInvoiceDTO> findByCriteria(CommercialInvoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialInvoice> specification = createSpecification(criteria);
        return commercialInvoiceMapper.toDto(commercialInvoiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialInvoiceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialInvoiceDTO> findByCriteria(CommercialInvoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialInvoice> specification = createSpecification(criteria);
        return commercialInvoiceRepository.findAll(specification, page)
            .map(commercialInvoiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialInvoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialInvoice> specification = createSpecification(criteria);
        return commercialInvoiceRepository.count(specification);
    }

    /**
     * Function to convert CommercialInvoiceCriteria to a {@link Specification}
     */
    private Specification<CommercialInvoice> createSpecification(CommercialInvoiceCriteria criteria) {
        Specification<CommercialInvoice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialInvoice_.id));
            }
            if (criteria.getInvoiceNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceNo(), CommercialInvoice_.invoiceNo));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceDate(), CommercialInvoice_.invoiceDate));
            }
            if (criteria.getTermsOfPayment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTermsOfPayment(), CommercialInvoice_.termsOfPayment));
            }
            if (criteria.getConsignedTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConsignedTo(), CommercialInvoice_.consignedTo));
            }
            if (criteria.getPortOfLoading() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortOfLoading(), CommercialInvoice_.portOfLoading));
            }
            if (criteria.getPortOfDischarge() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortOfDischarge(), CommercialInvoice_.portOfDischarge));
            }
            if (criteria.getExportRegistrationCertificateNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExportRegistrationCertificateNo(), CommercialInvoice_.exportRegistrationCertificateNo));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialInvoice_.createdBy));
            }
            if (criteria.getCreateOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateOn(), CommercialInvoice_.createOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialInvoice_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialInvoice_.updatedOn));
            }
            if (criteria.getCommercialPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPurchaseOrderId(),
                    root -> root.join(CommercialInvoice_.commercialPurchaseOrder, JoinType.LEFT).get(CommercialPurchaseOrder_.id)));
            }
            if (criteria.getCommercialProformaInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialProformaInvoiceId(),
                    root -> root.join(CommercialInvoice_.commercialProformaInvoice, JoinType.LEFT).get(CommercialProformaInvoice_.id)));
            }
            if (criteria.getCommercialPackagingId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPackagingId(),
                    root -> root.join(CommercialInvoice_.commercialPackaging, JoinType.LEFT).get(CommercialPackaging_.id)));
            }
        }
        return specification;
    }
}
