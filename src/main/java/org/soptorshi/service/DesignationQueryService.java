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

import org.soptorshi.domain.Designation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.DesignationRepository;
import org.soptorshi.repository.search.DesignationSearchRepository;
import org.soptorshi.service.dto.DesignationCriteria;
import org.soptorshi.service.dto.DesignationDTO;
import org.soptorshi.service.mapper.DesignationMapper;

/**
 * Service for executing complex queries for Designation entities in the database.
 * The main input is a {@link DesignationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DesignationDTO} or a {@link Page} of {@link DesignationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DesignationQueryService extends QueryService<Designation> {

    private final Logger log = LoggerFactory.getLogger(DesignationQueryService.class);

    private final DesignationRepository designationRepository;

    private final DesignationMapper designationMapper;

    private final DesignationSearchRepository designationSearchRepository;

    public DesignationQueryService(DesignationRepository designationRepository, DesignationMapper designationMapper, DesignationSearchRepository designationSearchRepository) {
        this.designationRepository = designationRepository;
        this.designationMapper = designationMapper;
        this.designationSearchRepository = designationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DesignationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DesignationDTO> findByCriteria(DesignationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Designation> specification = createSpecification(criteria);
        return designationMapper.toDto(designationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DesignationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DesignationDTO> findByCriteria(DesignationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Designation> specification = createSpecification(criteria);
        return designationRepository.findAll(specification, page)
            .map(designationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DesignationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Designation> specification = createSpecification(criteria);
        return designationRepository.count(specification);
    }

    /**
     * Function to convert DesignationCriteria to a {@link Specification}
     */
    private Specification<Designation> createSpecification(DesignationCriteria criteria) {
        Specification<Designation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Designation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Designation_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), Designation_.shortName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Designation_.description));
            }
        }
        return specification;
    }
}
