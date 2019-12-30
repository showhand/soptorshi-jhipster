package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPi_;
import org.soptorshi.domain.CommercialPo;
import org.soptorshi.domain.CommercialPo_;
import org.soptorshi.repository.CommercialPoRepository;
import org.soptorshi.repository.search.CommercialPoSearchRepository;
import org.soptorshi.service.dto.CommercialPoCriteria;
import org.soptorshi.service.dto.CommercialPoDTO;
import org.soptorshi.service.mapper.CommercialPoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for CommercialPo entities in the database.
 * The main input is a {@link CommercialPoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialPoDTO} or a {@link Page} of {@link CommercialPoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialPoQueryService extends QueryService<CommercialPo> {

    private final Logger log = LoggerFactory.getLogger(CommercialPoQueryService.class);

    private final CommercialPoRepository commercialPoRepository;

    private final CommercialPoMapper commercialPoMapper;

    private final CommercialPoSearchRepository commercialPoSearchRepository;

    public CommercialPoQueryService(CommercialPoRepository commercialPoRepository, CommercialPoMapper commercialPoMapper, CommercialPoSearchRepository commercialPoSearchRepository) {
        this.commercialPoRepository = commercialPoRepository;
        this.commercialPoMapper = commercialPoMapper;
        this.commercialPoSearchRepository = commercialPoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialPoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialPoDTO> findByCriteria(CommercialPoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialPo> specification = createSpecification(criteria);
        return commercialPoMapper.toDto(commercialPoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialPoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialPoDTO> findByCriteria(CommercialPoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialPo> specification = createSpecification(criteria);
        return commercialPoRepository.findAll(specification, page)
            .map(commercialPoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialPoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialPo> specification = createSpecification(criteria);
        return commercialPoRepository.count(specification);
    }

    /**
     * Function to convert CommercialPoCriteria to a {@link Specification}
     */
    private Specification<CommercialPo> createSpecification(CommercialPoCriteria criteria) {
        Specification<CommercialPo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialPo_.id));
            }
            if (criteria.getPurchaseOrderNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurchaseOrderNo(), CommercialPo_.purchaseOrderNo));
            }
            if (criteria.getPurchaseOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchaseOrderDate(), CommercialPo_.purchaseOrderDate));
            }
            if (criteria.getOriginOfGoods() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOriginOfGoods(), CommercialPo_.originOfGoods));
            }
            if (criteria.getFinalDestination() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinalDestination(), CommercialPo_.finalDestination));
            }
            if (criteria.getShipmentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShipmentDate(), CommercialPo_.shipmentDate));
            }
            if (criteria.getPoStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPoStatus(), CommercialPo_.poStatus));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialPo_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), CommercialPo_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialPo_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialPo_.updatedOn));
            }
            if (criteria.getCommercialPiId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPiId(),
                    root -> root.join(CommercialPo_.commercialPi, JoinType.LEFT).get(CommercialPi_.id)));
            }
        }
        return specification;
    }
}
