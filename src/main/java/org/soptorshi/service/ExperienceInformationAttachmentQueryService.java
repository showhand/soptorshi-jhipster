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

import org.soptorshi.domain.ExperienceInformationAttachment;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ExperienceInformationAttachmentRepository;
import org.soptorshi.repository.search.ExperienceInformationAttachmentSearchRepository;
import org.soptorshi.service.dto.ExperienceInformationAttachmentCriteria;
import org.soptorshi.service.dto.ExperienceInformationAttachmentDTO;
import org.soptorshi.service.mapper.ExperienceInformationAttachmentMapper;

/**
 * Service for executing complex queries for ExperienceInformationAttachment entities in the database.
 * The main input is a {@link ExperienceInformationAttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExperienceInformationAttachmentDTO} or a {@link Page} of {@link ExperienceInformationAttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExperienceInformationAttachmentQueryService extends QueryService<ExperienceInformationAttachment> {

    private final Logger log = LoggerFactory.getLogger(ExperienceInformationAttachmentQueryService.class);

    private final ExperienceInformationAttachmentRepository experienceInformationAttachmentRepository;

    private final ExperienceInformationAttachmentMapper experienceInformationAttachmentMapper;

    private final ExperienceInformationAttachmentSearchRepository experienceInformationAttachmentSearchRepository;

    public ExperienceInformationAttachmentQueryService(ExperienceInformationAttachmentRepository experienceInformationAttachmentRepository, ExperienceInformationAttachmentMapper experienceInformationAttachmentMapper, ExperienceInformationAttachmentSearchRepository experienceInformationAttachmentSearchRepository) {
        this.experienceInformationAttachmentRepository = experienceInformationAttachmentRepository;
        this.experienceInformationAttachmentMapper = experienceInformationAttachmentMapper;
        this.experienceInformationAttachmentSearchRepository = experienceInformationAttachmentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ExperienceInformationAttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExperienceInformationAttachmentDTO> findByCriteria(ExperienceInformationAttachmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExperienceInformationAttachment> specification = createSpecification(criteria);
        return experienceInformationAttachmentMapper.toDto(experienceInformationAttachmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExperienceInformationAttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExperienceInformationAttachmentDTO> findByCriteria(ExperienceInformationAttachmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExperienceInformationAttachment> specification = createSpecification(criteria);
        return experienceInformationAttachmentRepository.findAll(specification, page)
            .map(experienceInformationAttachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExperienceInformationAttachmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExperienceInformationAttachment> specification = createSpecification(criteria);
        return experienceInformationAttachmentRepository.count(specification);
    }

    /**
     * Function to convert ExperienceInformationAttachmentCriteria to a {@link Specification}
     */
    private Specification<ExperienceInformationAttachment> createSpecification(ExperienceInformationAttachmentCriteria criteria) {
        Specification<ExperienceInformationAttachment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ExperienceInformationAttachment_.id));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(ExperienceInformationAttachment_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
