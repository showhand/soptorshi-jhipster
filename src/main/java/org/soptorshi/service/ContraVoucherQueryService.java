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

import org.soptorshi.domain.ContraVoucher;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ContraVoucherRepository;
import org.soptorshi.repository.search.ContraVoucherSearchRepository;
import org.soptorshi.service.dto.ContraVoucherCriteria;
import org.soptorshi.service.dto.ContraVoucherDTO;
import org.soptorshi.service.mapper.ContraVoucherMapper;

/**
 * Service for executing complex queries for ContraVoucher entities in the database.
 * The main input is a {@link ContraVoucherCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContraVoucherDTO} or a {@link Page} of {@link ContraVoucherDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContraVoucherQueryService extends QueryService<ContraVoucher> {

    private final Logger log = LoggerFactory.getLogger(ContraVoucherQueryService.class);

    private final ContraVoucherRepository contraVoucherRepository;

    private final ContraVoucherMapper contraVoucherMapper;

    private final ContraVoucherSearchRepository contraVoucherSearchRepository;

    public ContraVoucherQueryService(ContraVoucherRepository contraVoucherRepository, ContraVoucherMapper contraVoucherMapper, ContraVoucherSearchRepository contraVoucherSearchRepository) {
        this.contraVoucherRepository = contraVoucherRepository;
        this.contraVoucherMapper = contraVoucherMapper;
        this.contraVoucherSearchRepository = contraVoucherSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ContraVoucherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContraVoucherDTO> findByCriteria(ContraVoucherCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContraVoucher> specification = createSpecification(criteria);
        return contraVoucherMapper.toDto(contraVoucherRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContraVoucherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContraVoucherDTO> findByCriteria(ContraVoucherCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContraVoucher> specification = createSpecification(criteria);
        return contraVoucherRepository.findAll(specification, page)
            .map(contraVoucherMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContraVoucherCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContraVoucher> specification = createSpecification(criteria);
        return contraVoucherRepository.count(specification);
    }

    /**
     * Function to convert ContraVoucherCriteria to a {@link Specification}
     */
    private Specification<ContraVoucher> createSpecification(ContraVoucherCriteria criteria) {
        Specification<ContraVoucher> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ContraVoucher_.id));
            }
            if (criteria.getVoucherNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoucherNo(), ContraVoucher_.voucherNo));
            }
            if (criteria.getVoucherDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherDate(), ContraVoucher_.voucherDate));
            }
            if (criteria.getPostDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostDate(), ContraVoucher_.postDate));
            }
            if (criteria.getApplicationType() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationType(), ContraVoucher_.applicationType));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationId(), ContraVoucher_.applicationId));
            }
            if (criteria.getConversionFactor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConversionFactor(), ContraVoucher_.conversionFactor));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ContraVoucher_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), ContraVoucher_.modifiedOn));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(ContraVoucher_.currency, JoinType.LEFT).get(Currency_.id)));
            }
        }
        return specification;
    }
}
