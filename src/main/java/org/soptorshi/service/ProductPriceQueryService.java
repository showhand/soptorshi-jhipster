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

import org.soptorshi.domain.ProductPrice;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ProductPriceRepository;
import org.soptorshi.repository.search.ProductPriceSearchRepository;
import org.soptorshi.service.dto.ProductPriceCriteria;
import org.soptorshi.service.dto.ProductPriceDTO;
import org.soptorshi.service.mapper.ProductPriceMapper;

/**
 * Service for executing complex queries for ProductPrice entities in the database.
 * The main input is a {@link ProductPriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductPriceDTO} or a {@link Page} of {@link ProductPriceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductPriceQueryService extends QueryService<ProductPrice> {

    private final Logger log = LoggerFactory.getLogger(ProductPriceQueryService.class);

    private final ProductPriceRepository productPriceRepository;

    private final ProductPriceMapper productPriceMapper;

    private final ProductPriceSearchRepository productPriceSearchRepository;

    public ProductPriceQueryService(ProductPriceRepository productPriceRepository, ProductPriceMapper productPriceMapper, ProductPriceSearchRepository productPriceSearchRepository) {
        this.productPriceRepository = productPriceRepository;
        this.productPriceMapper = productPriceMapper;
        this.productPriceSearchRepository = productPriceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProductPriceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductPriceDTO> findByCriteria(ProductPriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductPrice> specification = createSpecification(criteria);
        return productPriceMapper.toDto(productPriceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductPriceDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductPriceDTO> findByCriteria(ProductPriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductPrice> specification = createSpecification(criteria);
        return productPriceRepository.findAll(specification, page)
            .map(productPriceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductPriceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductPrice> specification = createSpecification(criteria);
        return productPriceRepository.count(specification);
    }

    /**
     * Function to convert ProductPriceCriteria to a {@link Specification}
     */
    private Specification<ProductPrice> createSpecification(ProductPriceCriteria criteria) {
        Specification<ProductPrice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProductPrice_.id));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), ProductPrice_.price));
            }
            if (criteria.getPriceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriceDate(), ProductPrice_.priceDate));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ProductPrice_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), ProductPrice_.modifiedOn));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductPrice_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
