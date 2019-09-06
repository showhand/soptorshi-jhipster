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

import org.soptorshi.domain.Voucher;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.VoucherRepository;
import org.soptorshi.repository.search.VoucherSearchRepository;
import org.soptorshi.service.dto.VoucherCriteria;
import org.soptorshi.service.dto.VoucherDTO;
import org.soptorshi.service.mapper.VoucherMapper;

/**
 * Service for executing complex queries for Voucher entities in the database.
 * The main input is a {@link VoucherCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VoucherDTO} or a {@link Page} of {@link VoucherDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VoucherQueryService extends QueryService<Voucher> {

    private final Logger log = LoggerFactory.getLogger(VoucherQueryService.class);

    private final VoucherRepository voucherRepository;

    private final VoucherMapper voucherMapper;

    private final VoucherSearchRepository voucherSearchRepository;

    public VoucherQueryService(VoucherRepository voucherRepository, VoucherMapper voucherMapper, VoucherSearchRepository voucherSearchRepository) {
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
        this.voucherSearchRepository = voucherSearchRepository;
    }

    /**
     * Return a {@link List} of {@link VoucherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VoucherDTO> findByCriteria(VoucherCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Voucher> specification = createSpecification(criteria);
        return voucherMapper.toDto(voucherRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VoucherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VoucherDTO> findByCriteria(VoucherCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Voucher> specification = createSpecification(criteria);
        return voucherRepository.findAll(specification, page)
            .map(voucherMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VoucherCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Voucher> specification = createSpecification(criteria);
        return voucherRepository.count(specification);
    }

    /**
     * Function to convert VoucherCriteria to a {@link Specification}
     */
    private Specification<Voucher> createSpecification(VoucherCriteria criteria) {
        Specification<Voucher> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Voucher_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Voucher_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), Voucher_.shortName));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedOn(), Voucher_.modifiedOn));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedBy(), Voucher_.modifiedBy));
            }
        }
        return specification;
    }
}
