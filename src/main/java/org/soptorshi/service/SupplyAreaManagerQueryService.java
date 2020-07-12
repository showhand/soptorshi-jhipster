package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.SupplyAreaManagerRepository;
import org.soptorshi.repository.search.SupplyAreaManagerSearchRepository;
import org.soptorshi.service.dto.SupplyAreaManagerCriteria;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;
import org.soptorshi.service.mapper.SupplyAreaManagerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplyAreaManager entities in the database.
 * The main input is a {@link SupplyAreaManagerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyAreaManagerDTO} or a {@link Page} of {@link SupplyAreaManagerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyAreaManagerQueryService extends QueryService<SupplyAreaManager> {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaManagerQueryService.class);

    private final SupplyAreaManagerRepository supplyAreaManagerRepository;

    private final SupplyAreaManagerMapper supplyAreaManagerMapper;

    private final SupplyAreaManagerSearchRepository supplyAreaManagerSearchRepository;

    public SupplyAreaManagerQueryService(SupplyAreaManagerRepository supplyAreaManagerRepository, SupplyAreaManagerMapper supplyAreaManagerMapper, SupplyAreaManagerSearchRepository supplyAreaManagerSearchRepository) {
        this.supplyAreaManagerRepository = supplyAreaManagerRepository;
        this.supplyAreaManagerMapper = supplyAreaManagerMapper;
        this.supplyAreaManagerSearchRepository = supplyAreaManagerSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyAreaManagerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyAreaManagerDTO> findByCriteria(SupplyAreaManagerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyAreaManager> specification = createSpecification(criteria);
        return supplyAreaManagerMapper.toDto(supplyAreaManagerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyAreaManagerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyAreaManagerDTO> findByCriteria(SupplyAreaManagerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyAreaManager> specification = createSpecification(criteria);
        return supplyAreaManagerRepository.findAll(specification, page)
            .map(supplyAreaManagerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyAreaManagerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyAreaManager> specification = createSpecification(criteria);
        return supplyAreaManagerRepository.count(specification);
    }

    /**
     * Function to convert SupplyAreaManagerCriteria to a {@link Specification}
     */
    private Specification<SupplyAreaManager> createSpecification(SupplyAreaManagerCriteria criteria) {
        Specification<SupplyAreaManager> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyAreaManager_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyAreaManager_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyAreaManager_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyAreaManager_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyAreaManager_.updatedOn));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), SupplyAreaManager_.endDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), SupplyAreaManager_.status));
            }
            if (criteria.getSupplyZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneId(),
                    root -> root.join(SupplyAreaManager_.supplyZone, JoinType.LEFT).get(SupplyZone_.id)));
            }
            if (criteria.getSupplyAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyAreaId(),
                    root -> root.join(SupplyAreaManager_.supplyArea, JoinType.LEFT).get(SupplyArea_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(SupplyAreaManager_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getSupplyZoneManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneManagerId(),
                    root -> root.join(SupplyAreaManager_.supplyZoneManager, JoinType.LEFT).get(SupplyZoneManager_.id)));
            }
        }
        return specification;
    }
}
