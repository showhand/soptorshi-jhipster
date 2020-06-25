package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyZone;
import org.soptorshi.domain.SupplyZone_;
import org.soptorshi.repository.SupplyZoneRepository;
import org.soptorshi.repository.search.SupplyZoneSearchRepository;
import org.soptorshi.service.dto.SupplyZoneCriteria;
import org.soptorshi.service.dto.SupplyZoneDTO;
import org.soptorshi.service.mapper.SupplyZoneMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for SupplyZone entities in the database.
 * The main input is a {@link SupplyZoneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplyZoneDTO} or a {@link Page} of {@link SupplyZoneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyZoneQueryService extends QueryService<SupplyZone> {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneQueryService.class);

    private final SupplyZoneRepository supplyZoneRepository;

    private final SupplyZoneMapper supplyZoneMapper;

    private final SupplyZoneSearchRepository supplyZoneSearchRepository;

    public SupplyZoneQueryService(SupplyZoneRepository supplyZoneRepository, SupplyZoneMapper supplyZoneMapper, SupplyZoneSearchRepository supplyZoneSearchRepository) {
        this.supplyZoneRepository = supplyZoneRepository;
        this.supplyZoneMapper = supplyZoneMapper;
        this.supplyZoneSearchRepository = supplyZoneSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SupplyZoneDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplyZoneDTO> findByCriteria(SupplyZoneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplyZone> specification = createSpecification(criteria);
        return supplyZoneMapper.toDto(supplyZoneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplyZoneDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplyZoneDTO> findByCriteria(SupplyZoneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplyZone> specification = createSpecification(criteria);
        return supplyZoneRepository.findAll(specification, page)
            .map(supplyZoneMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyZoneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplyZone> specification = createSpecification(criteria);
        return supplyZoneRepository.count(specification);
    }

    /**
     * Function to convert SupplyZoneCriteria to a {@link Specification}
     */
    private Specification<SupplyZone> createSpecification(SupplyZoneCriteria criteria) {
        Specification<SupplyZone> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SupplyZone_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SupplyZone_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SupplyZone_.code));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SupplyZone_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SupplyZone_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), SupplyZone_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), SupplyZone_.updatedOn));
            }
        }
        return specification;
    }
}
