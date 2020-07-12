package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyOrderDetailsRepository;
import org.soptorshi.repository.search.SupplyOrderDetailsSearchRepository;
import org.soptorshi.service.dto.SupplyOrderDetailsCriteria;
import org.soptorshi.service.dto.SupplyOrderDetailsDTO;
import org.soptorshi.service.mapper.SupplyOrderDetailsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplyOrderDetails entities in the database.
 * The main input is a {@link SupplyOrderDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyOrderDetailsDTO} or a {@link Page} of {@link SupplyOrderDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyOrderDetailsQueryService extends QueryService<SupplyOrderDetails> {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderDetailsQueryService.class);

    private final SupplyOrderDetailsRepository supplyOrderDetailsRepository;

    private final SupplyOrderDetailsMapper supplyOrderDetailsMapper;

    private final SupplyOrderDetailsSearchRepository supplyOrderDetailsSearchRepository;

    public SupplyOrderDetailsQueryService(SupplyOrderDetailsRepository supplyOrderDetailsRepository, SupplyOrderDetailsMapper supplyOrderDetailsMapper, SupplyOrderDetailsSearchRepository supplyOrderDetailsSearchRepository) {
        this.supplyOrderDetailsRepository = supplyOrderDetailsRepository;
        this.supplyOrderDetailsMapper = supplyOrderDetailsMapper;
        this.supplyOrderDetailsSearchRepository = supplyOrderDetailsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyOrderDetailsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyOrderDetailsDTO> findByCriteria(SupplyOrderDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyOrderDetails> specification = createSpecification(criteria);
        return supplyOrderDetailsMapper.toDto(supplyOrderDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyOrderDetailsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyOrderDetailsDTO> findByCriteria(SupplyOrderDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyOrderDetails> specification = createSpecification(criteria);
        return supplyOrderDetailsRepository.findAll(specification, page)
            .map(supplyOrderDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyOrderDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyOrderDetails> specification = createSpecification(criteria);
        return supplyOrderDetailsRepository.count(specification);
    }

    /**
     * Function to convert SupplyOrderDetailsCriteria to a {@link Specification}
     */
    private Specification<SupplyOrderDetails> createSpecification(SupplyOrderDetailsCriteria criteria) {
        Specification<SupplyOrderDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyOrderDetails_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyOrderDetails_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyOrderDetails_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyOrderDetails_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyOrderDetails_.updatedOn));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), SupplyOrderDetails_.quantity));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), SupplyOrderDetails_.price));
            }
            if (criteria.getSupplyOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyOrderId(),
                    root -> root.join(SupplyOrderDetails_.supplyOrder, JoinType.LEFT).get(SupplyOrder_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(SupplyOrderDetails_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(SupplyOrderDetails_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
