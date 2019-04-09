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

import org.soptorshi.domain.Attachment;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.AttachmentRepository;
import org.soptorshi.repository.search.AttachmentSearchRepository;
import org.soptorshi.service.dto.AttachmentCriteria;
import org.soptorshi.service.dto.AttachmentDTO;
import org.soptorshi.service.mapper.AttachmentMapper;

/**
 * Service for executing complex queries for Attachment entities in the database.
 * The main input is a {@link AttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttachmentDTO} or a {@link Page} of {@link AttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttachmentQueryService extends QueryService<Attachment> {

    private final Logger log = LoggerFactory.getLogger(AttachmentQueryService.class);

    private final AttachmentRepository attachmentRepository;

    private final AttachmentMapper attachmentMapper;

    private final AttachmentSearchRepository attachmentSearchRepository;

    public AttachmentQueryService(AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper, AttachmentSearchRepository attachmentSearchRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
        this.attachmentSearchRepository = attachmentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttachmentDTO> findByCriteria(AttachmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Attachment> specification = createSpecification(criteria);
        return attachmentMapper.toDto(attachmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttachmentDTO> findByCriteria(AttachmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Attachment> specification = createSpecification(criteria);
        return attachmentRepository.findAll(specification, page)
            .map(attachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttachmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Attachment> specification = createSpecification(criteria);
        return attachmentRepository.count(specification);
    }

    /**
     * Function to convert AttachmentCriteria to a {@link Specification}
     */
    private Specification<Attachment> createSpecification(AttachmentCriteria criteria) {
        Specification<Attachment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Attachment_.id));
            }
            if (criteria.getAcademicInformationId() != null) {
                specification = specification.and(buildSpecification(criteria.getAcademicInformationId(),
                    root -> root.join(Attachment_.academicInformation, JoinType.LEFT).get(AcademicInformation_.id)));
            }
            if (criteria.getTrainingInformationId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrainingInformationId(),
                    root -> root.join(Attachment_.trainingInformation, JoinType.LEFT).get(TrainingInformation_.id)));
            }
            if (criteria.getExperienceInformationId() != null) {
                specification = specification.and(buildSpecification(criteria.getExperienceInformationId(),
                    root -> root.join(Attachment_.experienceInformation, JoinType.LEFT).get(ExperienceInformation_.id)));
            }
        }
        return specification;
    }
}
