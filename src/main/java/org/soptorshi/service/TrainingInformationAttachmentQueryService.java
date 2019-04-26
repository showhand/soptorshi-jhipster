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

import org.soptorshi.domain.TrainingInformationAttachment;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.TrainingInformationAttachmentRepository;
import org.soptorshi.repository.search.TrainingInformationAttachmentSearchRepository;
import org.soptorshi.service.dto.TrainingInformationAttachmentCriteria;
import org.soptorshi.service.dto.TrainingInformationAttachmentDTO;
import org.soptorshi.service.mapper.TrainingInformationAttachmentMapper;

/**
 * Service for executing complex queries for TrainingInformationAttachment entities in the database.
 * The main input is a {@link TrainingInformationAttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrainingInformationAttachmentDTO} or a {@link Page} of {@link TrainingInformationAttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrainingInformationAttachmentQueryService extends QueryService<TrainingInformationAttachment> {

    private final Logger log = LoggerFactory.getLogger(TrainingInformationAttachmentQueryService.class);

    private final TrainingInformationAttachmentRepository trainingInformationAttachmentRepository;

    private final TrainingInformationAttachmentMapper trainingInformationAttachmentMapper;

    private final TrainingInformationAttachmentSearchRepository trainingInformationAttachmentSearchRepository;

    public TrainingInformationAttachmentQueryService(TrainingInformationAttachmentRepository trainingInformationAttachmentRepository, TrainingInformationAttachmentMapper trainingInformationAttachmentMapper, TrainingInformationAttachmentSearchRepository trainingInformationAttachmentSearchRepository) {
        this.trainingInformationAttachmentRepository = trainingInformationAttachmentRepository;
        this.trainingInformationAttachmentMapper = trainingInformationAttachmentMapper;
        this.trainingInformationAttachmentSearchRepository = trainingInformationAttachmentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TrainingInformationAttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrainingInformationAttachmentDTO> findByCriteria(TrainingInformationAttachmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TrainingInformationAttachment> specification = createSpecification(criteria);
        return trainingInformationAttachmentMapper.toDto(trainingInformationAttachmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TrainingInformationAttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingInformationAttachmentDTO> findByCriteria(TrainingInformationAttachmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TrainingInformationAttachment> specification = createSpecification(criteria);
        return trainingInformationAttachmentRepository.findAll(specification, page)
            .map(trainingInformationAttachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainingInformationAttachmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TrainingInformationAttachment> specification = createSpecification(criteria);
        return trainingInformationAttachmentRepository.count(specification);
    }

    /**
     * Function to convert TrainingInformationAttachmentCriteria to a {@link Specification}
     */
    private Specification<TrainingInformationAttachment> createSpecification(TrainingInformationAttachmentCriteria criteria) {
        Specification<TrainingInformationAttachment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TrainingInformationAttachment_.id));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(TrainingInformationAttachment_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
