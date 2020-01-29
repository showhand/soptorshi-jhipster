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

import org.soptorshi.domain.PaymentVoucher;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.PaymentVoucherRepository;
import org.soptorshi.repository.search.PaymentVoucherSearchRepository;
import org.soptorshi.service.dto.PaymentVoucherCriteria;
import org.soptorshi.service.dto.PaymentVoucherDTO;
import org.soptorshi.service.mapper.PaymentVoucherMapper;

/**
 * Service for executing complex queries for PaymentVoucher entities in the database.
 * The main input is a {@link PaymentVoucherCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentVoucherDTO} or a {@link Page} of {@link PaymentVoucherDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentVoucherQueryService extends QueryService<PaymentVoucher> {

    private final Logger log = LoggerFactory.getLogger(PaymentVoucherQueryService.class);

    private final PaymentVoucherRepository paymentVoucherRepository;

    private final PaymentVoucherMapper paymentVoucherMapper;

    private final PaymentVoucherSearchRepository paymentVoucherSearchRepository;

    public PaymentVoucherQueryService(PaymentVoucherRepository paymentVoucherRepository, PaymentVoucherMapper paymentVoucherMapper, PaymentVoucherSearchRepository paymentVoucherSearchRepository) {
        this.paymentVoucherRepository = paymentVoucherRepository;
        this.paymentVoucherMapper = paymentVoucherMapper;
        this.paymentVoucherSearchRepository = paymentVoucherSearchRepository;
    }

    /**
     * Return a {@link List} of {@link PaymentVoucherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentVoucherDTO> findByCriteria(PaymentVoucherCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentVoucher> specification = createSpecification(criteria);
        return paymentVoucherMapper.toDto(paymentVoucherRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentVoucherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentVoucherDTO> findByCriteria(PaymentVoucherCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentVoucher> specification = createSpecification(criteria);
        return paymentVoucherRepository.findAll(specification, page)
            .map(paymentVoucherMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentVoucherCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentVoucher> specification = createSpecification(criteria);
        return paymentVoucherRepository.count(specification);
    }

    /**
     * Function to convert PaymentVoucherCriteria to a {@link Specification}
     */
    private Specification<PaymentVoucher> createSpecification(PaymentVoucherCriteria criteria) {
        Specification<PaymentVoucher> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PaymentVoucher_.id));
            }
            if (criteria.getVoucherNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoucherNo(), PaymentVoucher_.voucherNo));
            }
            if (criteria.getVoucherDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherDate(), PaymentVoucher_.voucherDate));
            }
            if (criteria.getPostDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostDate(), PaymentVoucher_.postDate));
            }
            if (criteria.getApplicationType() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationType(), PaymentVoucher_.applicationType));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationId(), PaymentVoucher_.applicationId));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), PaymentVoucher_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), PaymentVoucher_.modifiedOn));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getAccountId(),
                    root -> root.join(PaymentVoucher_.account, JoinType.LEFT).get(MstAccount_.id)));
            }
        }
        return specification;
    }
}
