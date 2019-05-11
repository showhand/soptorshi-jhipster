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

import org.soptorshi.domain.Office;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.OfficeRepository;
import org.soptorshi.repository.search.OfficeSearchRepository;
import org.soptorshi.service.dto.OfficeCriteria;
import org.soptorshi.service.dto.OfficeDTO;
import org.soptorshi.service.mapper.OfficeMapper;

/**
 * Service for executing complex queries for Office entities in the database.
 * The main input is a {@link OfficeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OfficeDTO} or a {@link Page} of {@link OfficeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OfficeQueryService extends QueryService<Office> {

    private final Logger log = LoggerFactory.getLogger(OfficeQueryService.class);

    private final OfficeRepository officeRepository;

    private final OfficeMapper officeMapper;

    private final OfficeSearchRepository officeSearchRepository;

    public OfficeQueryService(OfficeRepository officeRepository, OfficeMapper officeMapper, OfficeSearchRepository officeSearchRepository) {
        this.officeRepository = officeRepository;
        this.officeMapper = officeMapper;
        this.officeSearchRepository = officeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link OfficeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OfficeDTO> findByCriteria(OfficeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Office> specification = createSpecification(criteria);
        return officeMapper.toDto(officeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OfficeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OfficeDTO> findByCriteria(OfficeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Office> specification = createSpecification(criteria);
        return officeRepository.findAll(specification, page)
            .map(officeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OfficeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Office> specification = createSpecification(criteria);
        return officeRepository.count(specification);
    }

    /**
     * Function to convert OfficeCriteria to a {@link Specification}
     */
    private Specification<Office> createSpecification(OfficeCriteria criteria) {
        Specification<Office> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Office_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Office_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Office_.description));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), Office_.location));
            }
        }
        return specification;
    }
}
