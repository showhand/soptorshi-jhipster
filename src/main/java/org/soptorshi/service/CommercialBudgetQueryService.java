package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialBudget;
import org.soptorshi.domain.CommercialBudget_;
import org.soptorshi.repository.CommercialBudgetRepository;
import org.soptorshi.repository.search.CommercialBudgetSearchRepository;
import org.soptorshi.service.dto.CommercialBudgetCriteria;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.service.mapper.CommercialBudgetMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for CommercialBudget entities in the database.
 * The main input is a {@link CommercialBudgetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialBudgetDTO} or a {@link Page} of {@link CommercialBudgetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialBudgetQueryService extends QueryService<CommercialBudget> {

    private final Logger log = LoggerFactory.getLogger(CommercialBudgetQueryService.class);

    private final CommercialBudgetRepository commercialBudgetRepository;

    private final CommercialBudgetMapper commercialBudgetMapper;

    private final CommercialBudgetSearchRepository commercialBudgetSearchRepository;

    public CommercialBudgetQueryService(CommercialBudgetRepository commercialBudgetRepository, CommercialBudgetMapper commercialBudgetMapper, CommercialBudgetSearchRepository commercialBudgetSearchRepository) {
        this.commercialBudgetRepository = commercialBudgetRepository;
        this.commercialBudgetMapper = commercialBudgetMapper;
        this.commercialBudgetSearchRepository = commercialBudgetSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialBudgetDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialBudgetDTO> findByCriteria(CommercialBudgetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialBudget> specification = createSpecification(criteria);
        return commercialBudgetMapper.toDto(commercialBudgetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialBudgetDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialBudgetDTO> findByCriteria(CommercialBudgetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialBudget> specification = createSpecification(criteria);
        return commercialBudgetRepository.findAll(specification, page)
            .map(commercialBudgetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialBudgetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialBudget> specification = createSpecification(criteria);
        return commercialBudgetRepository.count(specification);
    }

    /**
     * Function to convert CommercialBudgetCriteria to a {@link Specification}
     */
    private Specification<CommercialBudget> createSpecification(CommercialBudgetCriteria criteria) {
        Specification<CommercialBudget> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialBudget_.id));
            }
            if (criteria.getBudgetNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBudgetNo(), CommercialBudget_.budgetNo));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), CommercialBudget_.type));
            }
            if (criteria.getCustomer() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomer(), CommercialBudget_.customer));
            }
            if (criteria.getBudgetDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBudgetDate(), CommercialBudget_.budgetDate));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), CommercialBudget_.companyName));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentType(), CommercialBudget_.paymentType));
            }
            if (criteria.getTransportationType() != null) {
                specification = specification.and(buildSpecification(criteria.getTransportationType(), CommercialBudget_.transportationType));
            }
            if (criteria.getSeaPortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSeaPortName(), CommercialBudget_.seaPortName));
            }
            if (criteria.getSeaPortCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeaPortCost(), CommercialBudget_.seaPortCost));
            }
            if (criteria.getAirPortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAirPortName(), CommercialBudget_.airPortName));
            }
            if (criteria.getAirPortCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAirPortCost(), CommercialBudget_.airPortCost));
            }
            if (criteria.getLandPortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandPortName(), CommercialBudget_.landPortName));
            }
            if (criteria.getLandPortCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLandPortCost(), CommercialBudget_.landPortCost));
            }
            if (criteria.getInsurancePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInsurancePrice(), CommercialBudget_.insurancePrice));
            }
            if (criteria.getTotalTransportationCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalTransportationCost(), CommercialBudget_.totalTransportationCost));
            }
            if (criteria.getTotalQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalQuantity(), CommercialBudget_.totalQuantity));
            }
            if (criteria.getTotalOfferedPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalOfferedPrice(), CommercialBudget_.totalOfferedPrice));
            }
            if (criteria.getTotalBuyingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalBuyingPrice(), CommercialBudget_.totalBuyingPrice));
            }
            if (criteria.getProfitAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProfitAmount(), CommercialBudget_.profitAmount));
            }
            if (criteria.getProfitPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProfitPercentage(), CommercialBudget_.profitPercentage));
            }
            if (criteria.getBudgetStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getBudgetStatus(), CommercialBudget_.budgetStatus));
            }
            if (criteria.getProformaNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProformaNo(), CommercialBudget_.proformaNo));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialBudget_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), CommercialBudget_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialBudget_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialBudget_.updatedOn));
            }
        }
        return specification;
    }
}
