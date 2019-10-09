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

import org.soptorshi.domain.VoucherNumberControl;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.VoucherNumberControlRepository;
import org.soptorshi.repository.search.VoucherNumberControlSearchRepository;
import org.soptorshi.service.dto.VoucherNumberControlCriteria;
import org.soptorshi.service.dto.VoucherNumberControlDTO;
import org.soptorshi.service.mapper.VoucherNumberControlMapper;

/**
 * Service for executing complex queries for VoucherNumberControl entities in the database.
 * The main input is a {@link VoucherNumberControlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VoucherNumberControlDTO} or a {@link Page} of {@link VoucherNumberControlDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VoucherNumberControlQueryService extends QueryService<VoucherNumberControl> {

    private final Logger log = LoggerFactory.getLogger(VoucherNumberControlQueryService.class);

    private final VoucherNumberControlRepository voucherNumberControlRepository;

    private final VoucherNumberControlMapper voucherNumberControlMapper;

    private final VoucherNumberControlSearchRepository voucherNumberControlSearchRepository;

    public VoucherNumberControlQueryService(VoucherNumberControlRepository voucherNumberControlRepository, VoucherNumberControlMapper voucherNumberControlMapper, VoucherNumberControlSearchRepository voucherNumberControlSearchRepository) {
        this.voucherNumberControlRepository = voucherNumberControlRepository;
        this.voucherNumberControlMapper = voucherNumberControlMapper;
        this.voucherNumberControlSearchRepository = voucherNumberControlSearchRepository;
    }

    /**
     * Return a {@link List} of {@link VoucherNumberControlDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VoucherNumberControlDTO> findByCriteria(VoucherNumberControlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VoucherNumberControl> specification = createSpecification(criteria);
        return voucherNumberControlMapper.toDto(voucherNumberControlRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VoucherNumberControlDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VoucherNumberControlDTO> findByCriteria(VoucherNumberControlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VoucherNumberControl> specification = createSpecification(criteria);
        return voucherNumberControlRepository.findAll(specification, page)
            .map(voucherNumberControlMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VoucherNumberControlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VoucherNumberControl> specification = createSpecification(criteria);
        return voucherNumberControlRepository.count(specification);
    }

    /**
     * Function to convert VoucherNumberControlCriteria to a {@link Specification}
     */
    private Specification<VoucherNumberControl> createSpecification(VoucherNumberControlCriteria criteria) {
        Specification<VoucherNumberControl> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), VoucherNumberControl_.id));
            }
            if (criteria.getResetBasis() != null) {
                specification = specification.and(buildSpecification(criteria.getResetBasis(), VoucherNumberControl_.resetBasis));
            }
            if (criteria.getStartVoucherNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartVoucherNo(), VoucherNumberControl_.startVoucherNo));
            }
            if (criteria.getVoucherLimit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherLimit(), VoucherNumberControl_.voucherLimit));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), VoucherNumberControl_.modifiedOn));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), VoucherNumberControl_.modifiedBy));
            }
            if (criteria.getFinancialAccountYearId() != null) {
                specification = specification.and(buildSpecification(criteria.getFinancialAccountYearId(),
                    root -> root.join(VoucherNumberControl_.financialAccountYear, JoinType.LEFT).get(FinancialAccountYear_.id)));
            }
            if (criteria.getVoucherId() != null) {
                specification = specification.and(buildSpecification(criteria.getVoucherId(),
                    root -> root.join(VoucherNumberControl_.voucher, JoinType.LEFT).get(Voucher_.id)));
            }
        }
        return specification;
    }
}
