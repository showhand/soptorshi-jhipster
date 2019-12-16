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

import org.soptorshi.domain.HolidayType;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.HolidayTypeRepository;
import org.soptorshi.repository.search.HolidayTypeSearchRepository;
import org.soptorshi.service.dto.HolidayTypeCriteria;
import org.soptorshi.service.dto.HolidayTypeDTO;
import org.soptorshi.service.mapper.HolidayTypeMapper;

/**
 * Service for executing complex queries for HolidayType entities in the database.
 * The main input is a {@link HolidayTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HolidayTypeDTO} or a {@link Page} of {@link HolidayTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HolidayTypeQueryService extends QueryService<HolidayType> {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeQueryService.class);

    private final HolidayTypeRepository holidayTypeRepository;

    private final HolidayTypeMapper holidayTypeMapper;

    private final HolidayTypeSearchRepository holidayTypeSearchRepository;

    public HolidayTypeQueryService(HolidayTypeRepository holidayTypeRepository, HolidayTypeMapper holidayTypeMapper, HolidayTypeSearchRepository holidayTypeSearchRepository) {
        this.holidayTypeRepository = holidayTypeRepository;
        this.holidayTypeMapper = holidayTypeMapper;
        this.holidayTypeSearchRepository = holidayTypeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link HolidayTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HolidayTypeDTO> findByCriteria(HolidayTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HolidayType> specification = createSpecification(criteria);
        return holidayTypeMapper.toDto(holidayTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HolidayTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HolidayTypeDTO> findByCriteria(HolidayTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HolidayType> specification = createSpecification(criteria);
        return holidayTypeRepository.findAll(specification, page)
            .map(holidayTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HolidayTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HolidayType> specification = createSpecification(criteria);
        return holidayTypeRepository.count(specification);
    }

    /**
     * Function to convert HolidayTypeCriteria to a {@link Specification}
     */
    private Specification<HolidayType> createSpecification(HolidayTypeCriteria criteria) {
        Specification<HolidayType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), HolidayType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), HolidayType_.name));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), HolidayType_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), HolidayType_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), HolidayType_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), HolidayType_.updatedOn));
            }
        }
        return specification;
    }
}
