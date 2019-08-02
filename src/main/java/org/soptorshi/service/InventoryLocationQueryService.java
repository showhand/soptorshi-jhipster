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

import org.soptorshi.domain.InventoryLocation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.InventoryLocationRepository;
import org.soptorshi.repository.search.InventoryLocationSearchRepository;
import org.soptorshi.service.dto.InventoryLocationCriteria;
import org.soptorshi.service.dto.InventoryLocationDTO;
import org.soptorshi.service.mapper.InventoryLocationMapper;

/**
 * Service for executing complex queries for InventoryLocation entities in the database.
 * The main input is a {@link InventoryLocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventoryLocationDTO} or a {@link Page} of {@link InventoryLocationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryLocationQueryService extends QueryService<InventoryLocation> {

    private final Logger log = LoggerFactory.getLogger(InventoryLocationQueryService.class);

    private final InventoryLocationRepository inventoryLocationRepository;

    private final InventoryLocationMapper inventoryLocationMapper;

    private final InventoryLocationSearchRepository inventoryLocationSearchRepository;

    public InventoryLocationQueryService(InventoryLocationRepository inventoryLocationRepository, InventoryLocationMapper inventoryLocationMapper, InventoryLocationSearchRepository inventoryLocationSearchRepository) {
        this.inventoryLocationRepository = inventoryLocationRepository;
        this.inventoryLocationMapper = inventoryLocationMapper;
        this.inventoryLocationSearchRepository = inventoryLocationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link InventoryLocationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventoryLocationDTO> findByCriteria(InventoryLocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InventoryLocation> specification = createSpecification(criteria);
        return inventoryLocationMapper.toDto(inventoryLocationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InventoryLocationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryLocationDTO> findByCriteria(InventoryLocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventoryLocation> specification = createSpecification(criteria);
        return inventoryLocationRepository.findAll(specification, page)
            .map(inventoryLocationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryLocationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InventoryLocation> specification = createSpecification(criteria);
        return inventoryLocationRepository.count(specification);
    }

    /**
     * Function to convert InventoryLocationCriteria to a {@link Specification}
     */
    private Specification<InventoryLocation> createSpecification(InventoryLocationCriteria criteria) {
        Specification<InventoryLocation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), InventoryLocation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), InventoryLocation_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), InventoryLocation_.shortName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), InventoryLocation_.description));
            }
        }
        return specification;
    }
}
