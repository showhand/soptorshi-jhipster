package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Holiday;
import org.soptorshi.domain.HolidayType_;
import org.soptorshi.domain.Holiday_;
import org.soptorshi.repository.HolidayRepository;
import org.soptorshi.repository.search.HolidaySearchRepository;
import org.soptorshi.service.dto.HolidayCriteria;
import org.soptorshi.service.dto.HolidayDTO;
import org.soptorshi.service.mapper.HolidayMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for Holiday entities in the database.
 * The main input is a {@link HolidayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HolidayDTO} or a {@link Page} of {@link HolidayDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HolidayQueryService extends QueryService<Holiday> {

    private final Logger log = LoggerFactory.getLogger(HolidayQueryService.class);

    private final HolidayRepository holidayRepository;

    private final HolidayMapper holidayMapper;

    private final HolidaySearchRepository holidaySearchRepository;

    public HolidayQueryService(HolidayRepository holidayRepository, HolidayMapper holidayMapper, HolidaySearchRepository holidaySearchRepository) {
        this.holidayRepository = holidayRepository;
        this.holidayMapper = holidayMapper;
        this.holidaySearchRepository = holidaySearchRepository;
    }

    /**
     * Return a {@link List} of {@link HolidayDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HolidayDTO> findByCriteria(HolidayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Holiday> specification = createSpecification(criteria);
        return holidayMapper.toDto(holidayRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HolidayDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HolidayDTO> findByCriteria(HolidayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Holiday> specification = createSpecification(criteria);
        return holidayRepository.findAll(specification, page)
            .map(holidayMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HolidayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Holiday> specification = createSpecification(criteria);
        return holidayRepository.count(specification);
    }

    /**
     * Function to convert HolidayCriteria to a {@link Specification}
     */
    private Specification<Holiday> createSpecification(HolidayCriteria criteria) {
        Specification<Holiday> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Holiday_.id));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), Holiday_.fromDate));
            }
            if (criteria.getToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToDate(), Holiday_.toDate));
            }
            if (criteria.getNumberOfDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfDays(), Holiday_.numberOfDays));
            }
            if (criteria.getMoonDependency() != null) {
                specification = specification.and(buildSpecification(criteria.getMoonDependency(), Holiday_.moonDependency));
            }
            if (criteria.getHolidayDeclaredBy() != null) {
                specification = specification.and(buildSpecification(criteria.getHolidayDeclaredBy(), Holiday_.holidayDeclaredBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Holiday_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Holiday_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), Holiday_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), Holiday_.updatedOn));
            }
            if (criteria.getHolidayYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHolidayYear(), Holiday_.holidayYear));
            }
            if (criteria.getHolidayTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getHolidayTypeId(),
                    root -> root.join(Holiday_.holidayType, JoinType.LEFT).get(HolidayType_.id)));
            }
        }
        return specification;
    }
}
