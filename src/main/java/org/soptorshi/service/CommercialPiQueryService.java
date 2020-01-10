package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialBudget_;
import org.soptorshi.domain.CommercialPi;
import org.soptorshi.domain.CommercialPi_;
import org.soptorshi.repository.CommercialPiRepository;
import org.soptorshi.repository.search.CommercialPiSearchRepository;
import org.soptorshi.service.dto.CommercialPiCriteria;
import org.soptorshi.service.dto.CommercialPiDTO;
import org.soptorshi.service.mapper.CommercialPiMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for CommercialPi entities in the database.
 * The main input is a {@link CommercialPiCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialPiDTO} or a {@link Page} of {@link CommercialPiDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialPiQueryService extends QueryService<CommercialPi> {

    private final Logger log = LoggerFactory.getLogger(CommercialPiQueryService.class);

    private final CommercialPiRepository commercialPiRepository;

    private final CommercialPiMapper commercialPiMapper;

    private final CommercialPiSearchRepository commercialPiSearchRepository;

    public CommercialPiQueryService(CommercialPiRepository commercialPiRepository, CommercialPiMapper commercialPiMapper, CommercialPiSearchRepository commercialPiSearchRepository) {
        this.commercialPiRepository = commercialPiRepository;
        this.commercialPiMapper = commercialPiMapper;
        this.commercialPiSearchRepository = commercialPiSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialPiDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialPiDTO> findByCriteria(CommercialPiCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialPi> specification = createSpecification(criteria);
        return commercialPiMapper.toDto(commercialPiRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialPiDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialPiDTO> findByCriteria(CommercialPiCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialPi> specification = createSpecification(criteria);
        return commercialPiRepository.findAll(specification, page)
            .map(commercialPiMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialPiCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialPi> specification = createSpecification(criteria);
        return commercialPiRepository.count(specification);
    }

    /**
     * Function to convert CommercialPiCriteria to a {@link Specification}
     */
    private Specification<CommercialPi> createSpecification(CommercialPiCriteria criteria) {
        Specification<CommercialPi> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialPi_.id));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), CommercialPi_.companyName));
            }
            if (criteria.getCompanyDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyDescription(), CommercialPi_.companyDescription));
            }
            if (criteria.getProformaNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProformaNo(), CommercialPi_.proformaNo));
            }
            if (criteria.getProformaDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProformaDate(), CommercialPi_.proformaDate));
            }
            if (criteria.getHarmonicCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHarmonicCode(), CommercialPi_.harmonicCode));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentType(), CommercialPi_.paymentType));
            }
            if (criteria.getPaymentTerm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentTerm(), CommercialPi_.paymentTerm));
            }
            if (criteria.getTermsOfDelivery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTermsOfDelivery(), CommercialPi_.termsOfDelivery));
            }
            if (criteria.getShipmentDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShipmentDate(), CommercialPi_.shipmentDate));
            }
            if (criteria.getPortOfLoading() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortOfLoading(), CommercialPi_.portOfLoading));
            }
            if (criteria.getPortOfDestination() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortOfDestination(), CommercialPi_.portOfDestination));
            }
            if (criteria.getPurchaseOrderNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurchaseOrderNo(), CommercialPi_.purchaseOrderNo));
            }
            if (criteria.getPiStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPiStatus(), CommercialPi_.piStatus));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialPi_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), CommercialPi_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialPi_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialPi_.updatedOn));
            }
            if (criteria.getCommercialBudgetId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialBudgetId(),
                    root -> root.join(CommercialPi_.commercialBudget, JoinType.LEFT).get(CommercialBudget_.id)));
            }
        }
        return specification;
    }
}
