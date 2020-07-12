package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee_;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.SupplyZoneManager_;
import org.soptorshi.domain.SupplyZone_;
import org.soptorshi.repository.SupplyZoneManagerRepository;
import org.soptorshi.repository.search.SupplyZoneManagerSearchRepository;
import org.soptorshi.service.dto.SupplyZoneManagerCriteria;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
import org.soptorshi.service.mapper.SupplyZoneManagerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplyZoneManager entities in the database.
 * The main input is a {@link SupplyZoneManagerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyZoneManagerDTO} or a {@link Page} of {@link SupplyZoneManagerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyZoneManagerQueryService extends QueryService<SupplyZoneManager> {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneManagerQueryService.class);

    private final SupplyZoneManagerRepository supplyZoneManagerRepository;

    private final SupplyZoneManagerMapper supplyZoneManagerMapper;

    private final SupplyZoneManagerSearchRepository supplyZoneManagerSearchRepository;

    public SupplyZoneManagerQueryService(SupplyZoneManagerRepository supplyZoneManagerRepository, SupplyZoneManagerMapper supplyZoneManagerMapper, SupplyZoneManagerSearchRepository supplyZoneManagerSearchRepository) {
        this.supplyZoneManagerRepository = supplyZoneManagerRepository;
        this.supplyZoneManagerMapper = supplyZoneManagerMapper;
        this.supplyZoneManagerSearchRepository = supplyZoneManagerSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyZoneManagerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyZoneManagerDTO> findByCriteria(SupplyZoneManagerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyZoneManager> specification = createSpecification(criteria);
        return supplyZoneManagerMapper.toDto(supplyZoneManagerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyZoneManagerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyZoneManagerDTO> findByCriteria(SupplyZoneManagerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyZoneManager> specification = createSpecification(criteria);
        return supplyZoneManagerRepository.findAll(specification, page)
            .map(supplyZoneManagerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyZoneManagerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyZoneManager> specification = createSpecification(criteria);
        return supplyZoneManagerRepository.count(specification);
    }

    /**
     * Function to convert SupplyZoneManagerCriteria to a {@link Specification}
     */
    private Specification<SupplyZoneManager> createSpecification(SupplyZoneManagerCriteria criteria) {
        Specification<SupplyZoneManager> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyZoneManager_.id));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), SupplyZoneManager_.endDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyZoneManager_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyZoneManager_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyZoneManager_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyZoneManager_.updatedOn));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), SupplyZoneManager_.status));
            }
            if (criteria.getSupplyZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneId(),
                    root -> root.join(SupplyZoneManager_.supplyZone, JoinType.LEFT).get(SupplyZone_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(SupplyZoneManager_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
