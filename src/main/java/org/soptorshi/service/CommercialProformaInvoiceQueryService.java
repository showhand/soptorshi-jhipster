package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialProformaInvoice;
import org.soptorshi.domain.CommercialProformaInvoice_;
import org.soptorshi.domain.CommercialPurchaseOrder_;
import org.soptorshi.repository.CommercialProformaInvoiceRepository;
import org.soptorshi.repository.search.CommercialProformaInvoiceSearchRepository;
import org.soptorshi.service.dto.CommercialProformaInvoiceCriteria;
import org.soptorshi.service.dto.CommercialProformaInvoiceDTO;
import org.soptorshi.service.mapper.CommercialProformaInvoiceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for CommercialProformaInvoice entities in the database.
 * The main input is a {@link CommercialProformaInvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialProformaInvoiceDTO} or a {@link Page} of {@link CommercialProformaInvoiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialProformaInvoiceQueryService extends QueryService<CommercialProformaInvoice> {

    private final Logger log = LoggerFactory.getLogger(CommercialProformaInvoiceQueryService.class);

    private final CommercialProformaInvoiceRepository commercialProformaInvoiceRepository;

    private final CommercialProformaInvoiceMapper commercialProformaInvoiceMapper;

    private final CommercialProformaInvoiceSearchRepository commercialProformaInvoiceSearchRepository;

    public CommercialProformaInvoiceQueryService(CommercialProformaInvoiceRepository commercialProformaInvoiceRepository, CommercialProformaInvoiceMapper commercialProformaInvoiceMapper, CommercialProformaInvoiceSearchRepository commercialProformaInvoiceSearchRepository) {
        this.commercialProformaInvoiceRepository = commercialProformaInvoiceRepository;
        this.commercialProformaInvoiceMapper = commercialProformaInvoiceMapper;
        this.commercialProformaInvoiceSearchRepository = commercialProformaInvoiceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialProformaInvoiceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialProformaInvoiceDTO> findByCriteria(CommercialProformaInvoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialProformaInvoice> specification = createSpecification(criteria);
        return commercialProformaInvoiceMapper.toDto(commercialProformaInvoiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialProformaInvoiceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialProformaInvoiceDTO> findByCriteria(CommercialProformaInvoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialProformaInvoice> specification = createSpecification(criteria);
        return commercialProformaInvoiceRepository.findAll(specification, page)
            .map(commercialProformaInvoiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialProformaInvoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialProformaInvoice> specification = createSpecification(criteria);
        return commercialProformaInvoiceRepository.count(specification);
    }

    /**
     * Function to convert CommercialProformaInvoiceCriteria to a {@link Specification}
     */
    private Specification<CommercialProformaInvoice> createSpecification(CommercialProformaInvoiceCriteria criteria) {
        Specification<CommercialProformaInvoice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialProformaInvoice_.id));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), CommercialProformaInvoice_.companyName));
            }
            if (criteria.getCompanyDescriptionOrAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyDescriptionOrAddress(), CommercialProformaInvoice_.companyDescriptionOrAddress));
            }
            if (criteria.getProformaNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProformaNo(), CommercialProformaInvoice_.proformaNo));
            }
            if (criteria.getProformaDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProformaDate(), CommercialProformaInvoice_.proformaDate));
            }
            if (criteria.getHarmonicCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHarmonicCode(), CommercialProformaInvoice_.harmonicCode));
            }
            if (criteria.getPaymentTerm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentTerm(), CommercialProformaInvoice_.paymentTerm));
            }
            if (criteria.getTermsOfDelivery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTermsOfDelivery(), CommercialProformaInvoice_.termsOfDelivery));
            }
            if (criteria.getShipmentDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShipmentDate(), CommercialProformaInvoice_.shipmentDate));
            }
            if (criteria.getPortOfLanding() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortOfLanding(), CommercialProformaInvoice_.portOfLanding));
            }
            if (criteria.getPortOfDestination() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortOfDestination(), CommercialProformaInvoice_.portOfDestination));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialProformaInvoice_.createdBy));
            }
            if (criteria.getCreateOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateOn(), CommercialProformaInvoice_.createOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialProformaInvoice_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedOn(), CommercialProformaInvoice_.updatedOn));
            }
            if (criteria.getCommercialPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPurchaseOrderId(),
                    root -> root.join(CommercialProformaInvoice_.commercialPurchaseOrder, JoinType.LEFT).get(CommercialPurchaseOrder_.id)));
            }
        }
        return specification;
    }
}
