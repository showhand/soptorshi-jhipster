package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialWorkOrderDetails;
import org.soptorshi.domain.CommercialWorkOrderDetails_;
import org.soptorshi.domain.CommercialWorkOrder_;
import org.soptorshi.repository.CommercialWorkOrderDetailsRepository;
import org.soptorshi.repository.search.CommercialWorkOrderDetailsSearchRepository;
import org.soptorshi.service.dto.CommercialWorkOrderDetailsCriteria;
import org.soptorshi.service.dto.CommercialWorkOrderDetailsDTO;
import org.soptorshi.service.mapper.CommercialWorkOrderDetailsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for CommercialWorkOrderDetails entities in the database.
 * The main input is a {@link CommercialWorkOrderDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialWorkOrderDetailsDTO} or a {@link Page} of {@link CommercialWorkOrderDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialWorkOrderDetailsQueryService extends QueryService<CommercialWorkOrderDetails> {

    private final Logger log = LoggerFactory.getLogger(CommercialWorkOrderDetailsQueryService.class);

    private final CommercialWorkOrderDetailsRepository commercialWorkOrderDetailsRepository;

    private final CommercialWorkOrderDetailsMapper commercialWorkOrderDetailsMapper;

    private final CommercialWorkOrderDetailsSearchRepository commercialWorkOrderDetailsSearchRepository;

    public CommercialWorkOrderDetailsQueryService(CommercialWorkOrderDetailsRepository commercialWorkOrderDetailsRepository, CommercialWorkOrderDetailsMapper commercialWorkOrderDetailsMapper, CommercialWorkOrderDetailsSearchRepository commercialWorkOrderDetailsSearchRepository) {
        this.commercialWorkOrderDetailsRepository = commercialWorkOrderDetailsRepository;
        this.commercialWorkOrderDetailsMapper = commercialWorkOrderDetailsMapper;
        this.commercialWorkOrderDetailsSearchRepository = commercialWorkOrderDetailsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialWorkOrderDetailsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialWorkOrderDetailsDTO> findByCriteria(CommercialWorkOrderDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialWorkOrderDetails> specification = createSpecification(criteria);
        return commercialWorkOrderDetailsMapper.toDto(commercialWorkOrderDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialWorkOrderDetailsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialWorkOrderDetailsDTO> findByCriteria(CommercialWorkOrderDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialWorkOrderDetails> specification = createSpecification(criteria);
        return commercialWorkOrderDetailsRepository.findAll(specification, page)
            .map(commercialWorkOrderDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialWorkOrderDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialWorkOrderDetails> specification = createSpecification(criteria);
        return commercialWorkOrderDetailsRepository.count(specification);
    }

    /**
     * Function to convert CommercialWorkOrderDetailsCriteria to a {@link Specification}
     */
    private Specification<CommercialWorkOrderDetails> createSpecification(CommercialWorkOrderDetailsCriteria criteria) {
        Specification<CommercialWorkOrderDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialWorkOrderDetails_.id));
            }
            if (criteria.getGoods() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoods(), CommercialWorkOrderDetails_.goods));
            }
            if (criteria.getReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReason(), CommercialWorkOrderDetails_.reason));
            }
            if (criteria.getSize() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSize(), CommercialWorkOrderDetails_.size));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), CommercialWorkOrderDetails_.color));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), CommercialWorkOrderDetails_.quantity));
            }
            if (criteria.getCurrencyType() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyType(), CommercialWorkOrderDetails_.currencyType));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), CommercialWorkOrderDetails_.rate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialWorkOrderDetails_.createdBy));
            }
            if (criteria.getCreateOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateOn(), CommercialWorkOrderDetails_.createOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialWorkOrderDetails_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialWorkOrderDetails_.updatedOn));
            }
            if (criteria.getCommercialWorkOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialWorkOrderId(),
                    root -> root.join(CommercialWorkOrderDetails_.commercialWorkOrder, JoinType.LEFT).get(CommercialWorkOrder_.id)));
            }
        }
        return specification;
    }
}
