package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplySalesRepresentativeRepository;
import org.soptorshi.repository.search.SupplySalesRepresentativeSearchRepository;
import org.soptorshi.service.dto.SupplySalesRepresentativeCriteria;
import org.soptorshi.service.dto.SupplySalesRepresentativeDTO;
import org.soptorshi.service.mapper.SupplySalesRepresentativeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplySalesRepresentative entities in the database.
 * The main input is a {@link SupplySalesRepresentativeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplySalesRepresentativeDTO} or a {@link Page} of {@link SupplySalesRepresentativeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplySalesRepresentativeQueryService extends QueryService<SupplySalesRepresentative> {

    private final Logger log = LoggerFactory.getLogger(SupplySalesRepresentativeQueryService.class);

    private final SupplySalesRepresentativeRepository supplySalesRepresentativeRepository;

    private final SupplySalesRepresentativeMapper supplySalesRepresentativeMapper;

    private final SupplySalesRepresentativeSearchRepository supplySalesRepresentativeSearchRepository;

    public SupplySalesRepresentativeQueryService(SupplySalesRepresentativeRepository supplySalesRepresentativeRepository, SupplySalesRepresentativeMapper supplySalesRepresentativeMapper, SupplySalesRepresentativeSearchRepository supplySalesRepresentativeSearchRepository) {
        this.supplySalesRepresentativeRepository = supplySalesRepresentativeRepository;
        this.supplySalesRepresentativeMapper = supplySalesRepresentativeMapper;
        this.supplySalesRepresentativeSearchRepository = supplySalesRepresentativeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplySalesRepresentativeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplySalesRepresentativeDTO> findByCriteria(SupplySalesRepresentativeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplySalesRepresentative> specification = createSpecification(criteria);
        return supplySalesRepresentativeMapper.toDto(supplySalesRepresentativeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplySalesRepresentativeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplySalesRepresentativeDTO> findByCriteria(SupplySalesRepresentativeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplySalesRepresentative> specification = createSpecification(criteria);
        return supplySalesRepresentativeRepository.findAll(specification, page)
            .map(supplySalesRepresentativeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplySalesRepresentativeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplySalesRepresentative> specification = createSpecification(criteria);
        return supplySalesRepresentativeRepository.count(specification);
    }

    /**
     * Function to convert SupplySalesRepresentativeCriteria to a {@link Specification}
     */
    private Specification<SupplySalesRepresentative> createSpecification(SupplySalesRepresentativeCriteria criteria) {
        Specification<SupplySalesRepresentative> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplySalesRepresentative_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SupplySalesRepresentative_.name));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), SupplySalesRepresentative_.contact));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), SupplySalesRepresentative_.email));
            }
            if (criteria.getAdditionalInformation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdditionalInformation(), SupplySalesRepresentative_.additionalInformation));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplySalesRepresentative_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplySalesRepresentative_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplySalesRepresentative_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplySalesRepresentative_.updatedOn));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), SupplySalesRepresentative_.status));
            }
            if (criteria.getSupplyZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneId(),
                    root -> root.join(SupplySalesRepresentative_.supplyZone, JoinType.LEFT).get(SupplyZone_.id)));
            }
            if (criteria.getSupplyAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaId(),
                    root -> root.join(SupplySalesRepresentative_.supplyArea, JoinType.LEFT).get(SupplyArea_.id)));
            }
            if (criteria.getSupplyZoneManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneManagerId(),
                    root -> root.join(SupplySalesRepresentative_.supplyZoneManager, JoinType.LEFT).get(SupplyZoneManager_.id)));
            }
            if (criteria.getSupplyAreaManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaManagerId(),
                    root -> root.join(SupplySalesRepresentative_.supplyAreaManager, JoinType.LEFT).get(SupplyAreaManager_.id)));
            }
        }
        return specification;
    }
}
