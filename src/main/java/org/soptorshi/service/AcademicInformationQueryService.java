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

import org.soptorshi.domain.AcademicInformation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.AcademicInformationRepository;
import org.soptorshi.repository.search.AcademicInformationSearchRepository;
import org.soptorshi.service.dto.AcademicInformationCriteria;
import org.soptorshi.service.dto.AcademicInformationDTO;
import org.soptorshi.service.mapper.AcademicInformationMapper;

/**
 * Service for executing complex queries for AcademicInformation entities in the database.
 * The main input is a {@link AcademicInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AcademicInformationDTO} or a {@link Page} of {@link AcademicInformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AcademicInformationQueryService extends QueryService<AcademicInformation> {

    private final Logger log = LoggerFactory.getLogger(AcademicInformationQueryService.class);

    private final AcademicInformationRepository academicInformationRepository;

    private final AcademicInformationMapper academicInformationMapper;

    private final AcademicInformationSearchRepository academicInformationSearchRepository;

    public AcademicInformationQueryService(AcademicInformationRepository academicInformationRepository, AcademicInformationMapper academicInformationMapper, AcademicInformationSearchRepository academicInformationSearchRepository) {
        this.academicInformationRepository = academicInformationRepository;
        this.academicInformationMapper = academicInformationMapper;
        this.academicInformationSearchRepository = academicInformationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AcademicInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AcademicInformationDTO> findByCriteria(AcademicInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AcademicInformation> specification = createSpecification(criteria);
        return academicInformationMapper.toDto(academicInformationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AcademicInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AcademicInformationDTO> findByCriteria(AcademicInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AcademicInformation> specification = createSpecification(criteria);
        return academicInformationRepository.findAll(specification, page)
            .map(academicInformationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AcademicInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AcademicInformation> specification = createSpecification(criteria);
        return academicInformationRepository.count(specification);
    }

    /**
     * Function to convert AcademicInformationCriteria to a {@link Specification}
     */
    private Specification<AcademicInformation> createSpecification(AcademicInformationCriteria criteria) {
        Specification<AcademicInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AcademicInformation_.id));
            }
            if (criteria.getDegree() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDegree(), AcademicInformation_.degree));
            }
            if (criteria.getBoardOrUniversity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBoardOrUniversity(), AcademicInformation_.boardOrUniversity));
            }
            if (criteria.getPassingYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPassingYear(), AcademicInformation_.passingYear));
            }
            if (criteria.getGroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroup(), AcademicInformation_.group));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(AcademicInformation_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
