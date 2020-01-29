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

import org.soptorshi.domain.JournalVoucher;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.JournalVoucherRepository;
import org.soptorshi.repository.search.JournalVoucherSearchRepository;
import org.soptorshi.service.dto.JournalVoucherCriteria;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.mapper.JournalVoucherMapper;

/**
 * Service for executing complex queries for JournalVoucher entities in the database.
 * The main input is a {@link JournalVoucherCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JournalVoucherDTO} or a {@link Page} of {@link JournalVoucherDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JournalVoucherQueryService extends QueryService<JournalVoucher> {

    private final Logger log = LoggerFactory.getLogger(JournalVoucherQueryService.class);

    private final JournalVoucherRepository journalVoucherRepository;

    private final JournalVoucherMapper journalVoucherMapper;

    private final JournalVoucherSearchRepository journalVoucherSearchRepository;

    public JournalVoucherQueryService(JournalVoucherRepository journalVoucherRepository, JournalVoucherMapper journalVoucherMapper, JournalVoucherSearchRepository journalVoucherSearchRepository) {
        this.journalVoucherRepository = journalVoucherRepository;
        this.journalVoucherMapper = journalVoucherMapper;
        this.journalVoucherSearchRepository = journalVoucherSearchRepository;
    }

    /**
     * Return a {@link List} of {@link JournalVoucherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JournalVoucherDTO> findByCriteria(JournalVoucherCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JournalVoucher> specification = createSpecification(criteria);
        return journalVoucherMapper.toDto(journalVoucherRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JournalVoucherDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JournalVoucherDTO> findByCriteria(JournalVoucherCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JournalVoucher> specification = createSpecification(criteria);
        return journalVoucherRepository.findAll(specification, page)
            .map(journalVoucherMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JournalVoucherCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JournalVoucher> specification = createSpecification(criteria);
        return journalVoucherRepository.count(specification);
    }

    /**
     * Function to convert JournalVoucherCriteria to a {@link Specification}
     */
    private Specification<JournalVoucher> createSpecification(JournalVoucherCriteria criteria) {
        Specification<JournalVoucher> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), JournalVoucher_.id));
            }
            if (criteria.getVoucherNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoucherNo(), JournalVoucher_.voucherNo));
            }
            if (criteria.getVoucherDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherDate(), JournalVoucher_.voucherDate));
            }
            if (criteria.getPostDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostDate(), JournalVoucher_.postDate));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), JournalVoucher_.type));
            }
            if (criteria.getConversionFactor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConversionFactor(), JournalVoucher_.conversionFactor));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildSpecification(criteria.getReference(), JournalVoucher_.reference));
            }
            if (criteria.getApplicationType() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationType(), JournalVoucher_.applicationType));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationId(), JournalVoucher_.applicationId));
            }
            if (criteria.getReferenceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReferenceId(), JournalVoucher_.referenceId));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), JournalVoucher_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), JournalVoucher_.modifiedOn));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(JournalVoucher_.currency, JoinType.LEFT).get(Currency_.id)));
            }
        }
        return specification;
    }
}
