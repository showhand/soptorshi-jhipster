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

import org.soptorshi.domain.ReceiptVoucher;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ReceiptVoucherRepository;
import org.soptorshi.repository.search.ReceiptVoucherSearchRepository;
import org.soptorshi.service.dto.ReceiptVoucherCriteria;
import org.soptorshi.service.dto.ReceiptVoucherDTO;
import org.soptorshi.service.mapper.ReceiptVoucherMapper;

/**
 * Service for executing complex queries for ReceiptVoucher entities in the database.
 * The main input is a {@link ReceiptVoucherCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReceiptVoucherDTO} or a {@link Page} of {@link ReceiptVoucherDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReceiptVoucherQueryService extends QueryService<ReceiptVoucher> {

    private final Logger log = LoggerFactory.getLogger(ReceiptVoucherQueryService.class);

    private final ReceiptVoucherRepository receiptVoucherRepository;

    private final ReceiptVoucherMapper receiptVoucherMapper;

    private final ReceiptVoucherSearchRepository receiptVoucherSearchRepository;

    public ReceiptVoucherQueryService(ReceiptVoucherRepository receiptVoucherRepository, ReceiptVoucherMapper receiptVoucherMapper, ReceiptVoucherSearchRepository receiptVoucherSearchRepository) {
        this.receiptVoucherRepository = receiptVoucherRepository;
        this.receiptVoucherMapper = receiptVoucherMapper;
        this.receiptVoucherSearchRepository = receiptVoucherSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ReceiptVoucherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReceiptVoucherDTO> findByCriteria(ReceiptVoucherCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReceiptVoucher> specification = createSpecification(criteria);
        return receiptVoucherMapper.toDto(receiptVoucherRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReceiptVoucherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReceiptVoucherDTO> findByCriteria(ReceiptVoucherCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReceiptVoucher> specification = createSpecification(criteria);
        return receiptVoucherRepository.findAll(specification, page)
            .map(receiptVoucherMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReceiptVoucherCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReceiptVoucher> specification = createSpecification(criteria);
        return receiptVoucherRepository.count(specification);
    }

    /**
     * Function to convert ReceiptVoucherCriteria to a {@link Specification}
     */
    private Specification<ReceiptVoucher> createSpecification(ReceiptVoucherCriteria criteria) {
        Specification<ReceiptVoucher> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ReceiptVoucher_.id));
            }
            if (criteria.getVoucherNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoucherNo(), ReceiptVoucher_.voucherNo));
            }
            if (criteria.getVoucherDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherDate(), ReceiptVoucher_.voucherDate));
            }
            if (criteria.getPostDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostDate(), ReceiptVoucher_.postDate));
            }
            if (criteria.getApplicationType() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationType(), ReceiptVoucher_.applicationType));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationId(), ReceiptVoucher_.applicationId));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), ReceiptVoucher_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), ReceiptVoucher_.modifiedOn));
            }
            if (criteria.getAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getAccountId(),
                    root -> root.join(ReceiptVoucher_.account, JoinType.LEFT).get(MstAccount_.id)));
            }
        }
        return specification;
    }
}
