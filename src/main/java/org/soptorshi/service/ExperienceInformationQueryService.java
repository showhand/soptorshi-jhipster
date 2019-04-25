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

import org.soptorshi.domain.ExperienceInformation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ExperienceInformationRepository;
import org.soptorshi.repository.search.ExperienceInformationSearchRepository;
import org.soptorshi.service.dto.ExperienceInformationCriteria;
import org.soptorshi.service.dto.ExperienceInformationDTO;
import org.soptorshi.service.mapper.ExperienceInformationMapper;

/**
 * Service for executing complex queries for ExperienceInformation entities in the database.
 * The main input is a {@link ExperienceInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExperienceInformationDTO} or a {@link Page} of {@link ExperienceInformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExperienceInformationQueryService extends QueryService<ExperienceInformation> {

    private final Logger log = LoggerFactory.getLogger(ExperienceInformationQueryService.class);

    private final ExperienceInformationRepository experienceInformationRepository;

    private final ExperienceInformationMapper experienceInformationMapper;

    private final ExperienceInformationSearchRepository experienceInformationSearchRepository;

    public ExperienceInformationQueryService(ExperienceInformationRepository experienceInformationRepository, ExperienceInformationMapper experienceInformationMapper, ExperienceInformationSearchRepository experienceInformationSearchRepository) {
        this.experienceInformationRepository = experienceInformationRepository;
        this.experienceInformationMapper = experienceInformationMapper;
        this.experienceInformationSearchRepository = experienceInformationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ExperienceInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExperienceInformationDTO> findByCriteria(ExperienceInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExperienceInformation> specification = createSpecification(criteria);
        return experienceInformationMapper.toDto(experienceInformationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExperienceInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExperienceInformationDTO> findByCriteria(ExperienceInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExperienceInformation> specification = createSpecification(criteria);
        return experienceInformationRepository.findAll(specification, page)
            .map(experienceInformationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExperienceInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExperienceInformation> specification = createSpecification(criteria);
        return experienceInformationRepository.count(specification);
    }

    /**
     * Function to convert ExperienceInformationCriteria to a {@link Specification}
     */
    private Specification<ExperienceInformation> createSpecification(ExperienceInformationCriteria criteria) {
        Specification<ExperienceInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ExperienceInformation_.id));
            }
            if (criteria.getOrganization() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganization(), ExperienceInformation_.organization));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), ExperienceInformation_.designation));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ExperienceInformation_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ExperienceInformation_.endDate));
            }
            if (criteria.getEmploymentType() != null) {
                specification = specification.and(buildSpecification(criteria.getEmploymentType(), ExperienceInformation_.employmentType));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(ExperienceInformation_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
