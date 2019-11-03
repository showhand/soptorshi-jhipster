package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPackagingDetails;
import org.soptorshi.domain.CommercialPackagingDetails_;
import org.soptorshi.domain.CommercialPackaging_;
import org.soptorshi.repository.CommercialPackagingDetailsRepository;
import org.soptorshi.repository.search.CommercialPackagingDetailsSearchRepository;
import org.soptorshi.service.dto.CommercialPackagingDetailsCriteria;
import org.soptorshi.service.dto.CommercialPackagingDetailsDTO;
import org.soptorshi.service.mapper.CommercialPackagingDetailsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for CommercialPackagingDetails entities in the database.
 * The main input is a {@link CommercialPackagingDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialPackagingDetailsDTO} or a {@link Page} of {@link CommercialPackagingDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialPackagingDetailsQueryService extends QueryService<CommercialPackagingDetails> {

    private final Logger log = LoggerFactory.getLogger(CommercialPackagingDetailsQueryService.class);

    private final CommercialPackagingDetailsRepository commercialPackagingDetailsRepository;

    private final CommercialPackagingDetailsMapper commercialPackagingDetailsMapper;

    private final CommercialPackagingDetailsSearchRepository commercialPackagingDetailsSearchRepository;

    public CommercialPackagingDetailsQueryService(CommercialPackagingDetailsRepository commercialPackagingDetailsRepository, CommercialPackagingDetailsMapper commercialPackagingDetailsMapper, CommercialPackagingDetailsSearchRepository commercialPackagingDetailsSearchRepository) {
        this.commercialPackagingDetailsRepository = commercialPackagingDetailsRepository;
        this.commercialPackagingDetailsMapper = commercialPackagingDetailsMapper;
        this.commercialPackagingDetailsSearchRepository = commercialPackagingDetailsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialPackagingDetailsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialPackagingDetailsDTO> findByCriteria(CommercialPackagingDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialPackagingDetails> specification = createSpecification(criteria);
        return commercialPackagingDetailsMapper.toDto(commercialPackagingDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialPackagingDetailsDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialPackagingDetailsDTO> findByCriteria(CommercialPackagingDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialPackagingDetails> specification = createSpecification(criteria);
        return commercialPackagingDetailsRepository.findAll(specification, page)
            .map(commercialPackagingDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialPackagingDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialPackagingDetails> specification = createSpecification(criteria);
        return commercialPackagingDetailsRepository.count(specification);
    }

    /**
     * Function to convert CommercialPackagingDetailsCriteria to a {@link Specification}
     */
    private Specification<CommercialPackagingDetails> createSpecification(CommercialPackagingDetailsCriteria criteria) {
        Specification<CommercialPackagingDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialPackagingDetails_.id));
            }
            if (criteria.getProDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProDate(), CommercialPackagingDetails_.proDate));
            }
            if (criteria.getExpDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpDate(), CommercialPackagingDetails_.expDate));
            }
            if (criteria.getShift1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShift1(), CommercialPackagingDetails_.shift1));
            }
            if (criteria.getShift1Total() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShift1Total(), CommercialPackagingDetails_.shift1Total));
            }
            if (criteria.getShift2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShift2(), CommercialPackagingDetails_.shift2));
            }
            if (criteria.getShift2Total() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getShift2Total(), CommercialPackagingDetails_.shift2Total));
            }
            if (criteria.getDayTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDayTotal(), CommercialPackagingDetails_.dayTotal));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), CommercialPackagingDetails_.total));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), CommercialPackagingDetails_.createdBy));
            }
            if (criteria.getCreateOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateOn(), CommercialPackagingDetails_.createOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), CommercialPackagingDetails_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedOn(), CommercialPackagingDetails_.updatedOn));
            }
            if (criteria.getCommercialPackagingId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPackagingId(),
                    root -> root.join(CommercialPackagingDetails_.commercialPackaging, JoinType.LEFT).get(CommercialPackaging_.id)));
            }
        }
        return specification;
    }
}
