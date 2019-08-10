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

import org.soptorshi.domain.InventorySubLocation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.InventorySubLocationRepository;
import org.soptorshi.repository.search.InventorySubLocationSearchRepository;
import org.soptorshi.service.dto.InventorySubLocationCriteria;
import org.soptorshi.service.dto.InventorySubLocationDTO;
import org.soptorshi.service.mapper.InventorySubLocationMapper;

/**
 * Service for executing complex queries for InventorySubLocation entities in the database.
 * The main input is a {@link InventorySubLocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventorySubLocationDTO} or a {@link Page} of {@link InventorySubLocationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventorySubLocationQueryService extends QueryService<InventorySubLocation> {

    private final Logger log = LoggerFactory.getLogger(InventorySubLocationQueryService.class);

    private final InventorySubLocationRepository inventorySubLocationRepository;

    private final InventorySubLocationMapper inventorySubLocationMapper;

    private final InventorySubLocationSearchRepository inventorySubLocationSearchRepository;

    public InventorySubLocationQueryService(InventorySubLocationRepository inventorySubLocationRepository, InventorySubLocationMapper inventorySubLocationMapper, InventorySubLocationSearchRepository inventorySubLocationSearchRepository) {
        this.inventorySubLocationRepository = inventorySubLocationRepository;
        this.inventorySubLocationMapper = inventorySubLocationMapper;
        this.inventorySubLocationSearchRepository = inventorySubLocationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link InventorySubLocationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventorySubLocationDTO> findByCriteria(InventorySubLocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InventorySubLocation> specification = createSpecification(criteria);
        return inventorySubLocationMapper.toDto(inventorySubLocationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InventorySubLocationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventorySubLocationDTO> findByCriteria(InventorySubLocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventorySubLocation> specification = createSpecification(criteria);
        return inventorySubLocationRepository.findAll(specification, page)
            .map(inventorySubLocationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventorySubLocationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InventorySubLocation> specification = createSpecification(criteria);
        return inventorySubLocationRepository.count(specification);
    }

    /**
     * Function to convert InventorySubLocationCriteria to a {@link Specification}
     */
    private Specification<InventorySubLocation> createSpecification(InventorySubLocationCriteria criteria) {
        Specification<InventorySubLocation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), InventorySubLocation_.id));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getCategory(), InventorySubLocation_.category));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), InventorySubLocation_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), InventorySubLocation_.shortName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), InventorySubLocation_.description));
            }
            if (criteria.getInventoryLocationsId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoryLocationsId(),
                    root -> root.join(InventorySubLocation_.inventoryLocations, JoinType.LEFT).get(InventoryLocation_.id)));
            }
        }
        return specification;
    }
}
