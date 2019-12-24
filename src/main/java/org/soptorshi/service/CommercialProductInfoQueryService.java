package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.CommercialProductInfoRepository;
import org.soptorshi.repository.search.CommercialProductInfoSearchRepository;
import org.soptorshi.service.dto.CommercialProductInfoCriteria;
import org.soptorshi.service.dto.CommercialProductInfoDTO;
import org.soptorshi.service.mapper.CommercialProductInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for CommercialProductInfo entities in the database.
 * The main input is a {@link CommercialProductInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialProductInfoDTO} or a {@link Page} of {@link CommercialProductInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialProductInfoQueryService extends QueryService<CommercialProductInfo> {

    private final Logger log = LoggerFactory.getLogger(CommercialProductInfoQueryService.class);

    private final CommercialProductInfoRepository commercialProductInfoRepository;

    private final CommercialProductInfoMapper commercialProductInfoMapper;

    private final CommercialProductInfoSearchRepository commercialProductInfoSearchRepository;

    public CommercialProductInfoQueryService(CommercialProductInfoRepository commercialProductInfoRepository, CommercialProductInfoMapper commercialProductInfoMapper, CommercialProductInfoSearchRepository commercialProductInfoSearchRepository) {
        this.commercialProductInfoRepository = commercialProductInfoRepository;
        this.commercialProductInfoMapper = commercialProductInfoMapper;
        this.commercialProductInfoSearchRepository = commercialProductInfoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialProductInfoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialProductInfoDTO> findByCriteria(CommercialProductInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialProductInfo> specification = createSpecification(criteria);
        return commercialProductInfoMapper.toDto(commercialProductInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialProductInfoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialProductInfoDTO> findByCriteria(CommercialProductInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialProductInfo> specification = createSpecification(criteria);
        return commercialProductInfoRepository.findAll(specification, page)
            .map(commercialProductInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialProductInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialProductInfo> specification = createSpecification(criteria);
        return commercialProductInfoRepository.count(specification);
    }

    /**
     * Function to convert CommercialProductInfoCriteria to a {@link Specification}
     */
    private Specification<CommercialProductInfo> createSpecification(CommercialProductInfoCriteria criteria) {
        Specification<CommercialProductInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialProductInfo_.id));
            }
            if (criteria.getSerialNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSerialNo(), CommercialProductInfo_.serialNo));
            }
            if (criteria.getPackagingDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPackagingDescription(), CommercialProductInfo_.packagingDescription));
            }
            if (criteria.getOthersDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOthersDescription(), CommercialProductInfo_.othersDescription));
            }
            if (criteria.getOfferedQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOfferedQuantity(), CommercialProductInfo_.offeredQuantity));
            }
            if (criteria.getOfferedUnit() != null) {
                specification = specification.and(buildSpecification(criteria.getOfferedUnit(), CommercialProductInfo_.offeredUnit));
            }
            if (criteria.getOfferedUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOfferedUnitPrice(), CommercialProductInfo_.offeredUnitPrice));
            }
            if (criteria.getOfferedTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOfferedTotalPrice(), CommercialProductInfo_.offeredTotalPrice));
            }
            if (criteria.getBuyingQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBuyingQuantity(), CommercialProductInfo_.buyingQuantity));
            }
            if (criteria.getBuyingUnit() != null) {
                specification = specification.and(buildSpecification(criteria.getBuyingUnit(), CommercialProductInfo_.buyingUnit));
            }
            if (criteria.getBuyingUnitPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBuyingUnitPrice(), CommercialProductInfo_.buyingUnitPrice));
            }
            if (criteria.getBuyingTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBuyingTotalPrice(), CommercialProductInfo_.buyingTotalPrice));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialProductInfo_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), CommercialProductInfo_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialProductInfo_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialProductInfo_.updatedOn));
            }
            if (criteria.getCommercialBudgetId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialBudgetId(),
                    root -> root.join(CommercialProductInfo_.commercialBudget, JoinType.LEFT).get(CommercialBudget_.id)));
            }
            if (criteria.getProductCategoriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoriesId(),
                    root -> root.join(CommercialProductInfo_.productCategories, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductsId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductsId(),
                    root -> root.join(CommercialProductInfo_.products, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
