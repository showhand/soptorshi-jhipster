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

import org.soptorshi.domain.TrainingInformation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.TrainingInformationRepository;
import org.soptorshi.repository.search.TrainingInformationSearchRepository;
import org.soptorshi.service.dto.TrainingInformationCriteria;
import org.soptorshi.service.dto.TrainingInformationDTO;
import org.soptorshi.service.mapper.TrainingInformationMapper;

/**
 * Service for executing complex queries for TrainingInformation entities in the database.
 * The main input is a {@link TrainingInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrainingInformationDTO} or a {@link Page} of {@link TrainingInformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrainingInformationQueryService extends QueryService<TrainingInformation> {

    private final Logger log = LoggerFactory.getLogger(TrainingInformationQueryService.class);

    private final TrainingInformationRepository trainingInformationRepository;

    private final TrainingInformationMapper trainingInformationMapper;

    private final TrainingInformationSearchRepository trainingInformationSearchRepository;

    public TrainingInformationQueryService(TrainingInformationRepository trainingInformationRepository, TrainingInformationMapper trainingInformationMapper, TrainingInformationSearchRepository trainingInformationSearchRepository) {
        this.trainingInformationRepository = trainingInformationRepository;
        this.trainingInformationMapper = trainingInformationMapper;
        this.trainingInformationSearchRepository = trainingInformationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TrainingInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrainingInformationDTO> findByCriteria(TrainingInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TrainingInformation> specification = createSpecification(criteria);
        return trainingInformationMapper.toDto(trainingInformationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TrainingInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingInformationDTO> findByCriteria(TrainingInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TrainingInformation> specification = createSpecification(criteria);
        return trainingInformationRepository.findAll(specification, page)
            .map(trainingInformationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainingInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TrainingInformation> specification = createSpecification(criteria);
        return trainingInformationRepository.count(specification);
    }

    /**
     * Function to convert TrainingInformationCriteria to a {@link Specification}
     */
    private Specification<TrainingInformation> createSpecification(TrainingInformationCriteria criteria) {
        Specification<TrainingInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TrainingInformation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TrainingInformation_.name));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), TrainingInformation_.subject));
            }
            if (criteria.getOrganization() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganization(), TrainingInformation_.organization));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(TrainingInformation_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
