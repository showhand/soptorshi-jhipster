package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyArea;
import org.soptorshi.domain.SupplyArea_;
import org.soptorshi.domain.SupplyZoneManager_;
import org.soptorshi.domain.SupplyZone_;
import org.soptorshi.repository.SupplyAreaRepository;
import org.soptorshi.repository.search.SupplyAreaSearchRepository;
import org.soptorshi.service.dto.SupplyAreaCriteria;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.mapper.SupplyAreaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for SupplyArea entities in the database.
 * The main input is a {@link SupplyAreaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyAreaDTO} or a {@link Page} of {@link SupplyAreaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyAreaQueryService extends QueryService<SupplyArea> {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaQueryService.class);

    private final SupplyAreaRepository supplyAreaRepository;

    private final SupplyAreaMapper supplyAreaMapper;

    private final SupplyAreaSearchRepository supplyAreaSearchRepository;

    public SupplyAreaQueryService(SupplyAreaRepository supplyAreaRepository, SupplyAreaMapper supplyAreaMapper, SupplyAreaSearchRepository supplyAreaSearchRepository) {
        this.supplyAreaRepository = supplyAreaRepository;
        this.supplyAreaMapper = supplyAreaMapper;
        this.supplyAreaSearchRepository = supplyAreaSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyAreaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyAreaDTO> findByCriteria(SupplyAreaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyArea> specification = createSpecification(criteria);
        return supplyAreaMapper.toDto(supplyAreaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyAreaDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyAreaDTO> findByCriteria(SupplyAreaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyArea> specification = createSpecification(criteria);
        return supplyAreaRepository.findAll(specification, page)
            .map(supplyAreaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyAreaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyArea> specification = createSpecification(criteria);
        return supplyAreaRepository.count(specification);
    }

    /**
     * Function to convert SupplyAreaCriteria to a {@link Specification}
     */
    private Specification<SupplyArea> createSpecification(SupplyAreaCriteria criteria) {
        Specification<SupplyArea> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyArea_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SupplyArea_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SupplyArea_.code));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyArea_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyArea_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyArea_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyArea_.updatedOn));
            }
            if (criteria.getSupplyZoneId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneId(),
                    root -> root.join(SupplyArea_.supplyZone, JoinType.LEFT).get(SupplyZone_.id)));
            }
            if (criteria.getSupplyZoneManagerId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplyZoneManagerId(),
                    root -> root.join(SupplyArea_.supplyZoneManager, JoinType.LEFT).get(SupplyZoneManager_.id)));
            }
        }
        return specification;
    }
}
