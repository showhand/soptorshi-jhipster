package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPaymentInfo;
import org.soptorshi.domain.CommercialPaymentInfo_;
import org.soptorshi.domain.CommercialPi_;
import org.soptorshi.repository.CommercialPaymentInfoRepository;
import org.soptorshi.repository.search.CommercialPaymentInfoSearchRepository;
import org.soptorshi.service.dto.CommercialPaymentInfoCriteria;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;
import org.soptorshi.service.mapper.CommercialPaymentInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for CommercialPaymentInfo entities in the database.
 * The main input is a {@link CommercialPaymentInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialPaymentInfoDTO} or a {@link Page} of {@link CommercialPaymentInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialPaymentInfoQueryService extends QueryService<CommercialPaymentInfo> {

    private final Logger log = LoggerFactory.getLogger(CommercialPaymentInfoQueryService.class);

    private final CommercialPaymentInfoRepository commercialPaymentInfoRepository;

    private final CommercialPaymentInfoMapper commercialPaymentInfoMapper;

    private final CommercialPaymentInfoSearchRepository commercialPaymentInfoSearchRepository;

    public CommercialPaymentInfoQueryService(CommercialPaymentInfoRepository commercialPaymentInfoRepository, CommercialPaymentInfoMapper commercialPaymentInfoMapper, CommercialPaymentInfoSearchRepository commercialPaymentInfoSearchRepository) {
        this.commercialPaymentInfoRepository = commercialPaymentInfoRepository;
        this.commercialPaymentInfoMapper = commercialPaymentInfoMapper;
        this.commercialPaymentInfoSearchRepository = commercialPaymentInfoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialPaymentInfoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialPaymentInfoDTO> findByCriteria(CommercialPaymentInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialPaymentInfo> specification = createSpecification(criteria);
        return commercialPaymentInfoMapper.toDto(commercialPaymentInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialPaymentInfoDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialPaymentInfoDTO> findByCriteria(CommercialPaymentInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialPaymentInfo> specification = createSpecification(criteria);
        return commercialPaymentInfoRepository.findAll(specification, page)
            .map(commercialPaymentInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialPaymentInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialPaymentInfo> specification = createSpecification(criteria);
        return commercialPaymentInfoRepository.count(specification);
    }

    /**
     * Function to convert CommercialPaymentInfoCriteria to a {@link Specification}
     */
    private Specification<CommercialPaymentInfo> createSpecification(CommercialPaymentInfoCriteria criteria) {
        Specification<CommercialPaymentInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialPaymentInfo_.id));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentType(), CommercialPaymentInfo_.paymentType));
            }
            if (criteria.getTotalAmountToPay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmountToPay(), CommercialPaymentInfo_.totalAmountToPay));
            }
            if (criteria.getTotalAmountPaid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmountPaid(), CommercialPaymentInfo_.totalAmountPaid));
            }
            if (criteria.getRemainingAmountToPay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRemainingAmountToPay(), CommercialPaymentInfo_.remainingAmountToPay));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentStatus(), CommercialPaymentInfo_.paymentStatus));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), CommercialPaymentInfo_.createdOn));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialPaymentInfo_.createdBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), CommercialPaymentInfo_.updatedOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialPaymentInfo_.updatedBy));
            }
            if (criteria.getCommercialPiId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPiId(),
                    root -> root.join(CommercialPaymentInfo_.commercialPi, JoinType.LEFT).get(CommercialPi_.id)));
            }
        }
        return specification;
    }
}
