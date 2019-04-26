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

import org.soptorshi.domain.AcademicInformationAttachment;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.AcademicInformationAttachmentRepository;
import org.soptorshi.repository.search.AcademicInformationAttachmentSearchRepository;
import org.soptorshi.service.dto.AcademicInformationAttachmentCriteria;
import org.soptorshi.service.dto.AcademicInformationAttachmentDTO;
import org.soptorshi.service.mapper.AcademicInformationAttachmentMapper;

/**
 * Service for executing complex queries for AcademicInformationAttachment entities in the database.
 * The main input is a {@link AcademicInformationAttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AcademicInformationAttachmentDTO} or a {@link Page} of {@link AcademicInformationAttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AcademicInformationAttachmentQueryService extends QueryService<AcademicInformationAttachment> {

    private final Logger log = LoggerFactory.getLogger(AcademicInformationAttachmentQueryService.class);

    private final AcademicInformationAttachmentRepository academicInformationAttachmentRepository;

    private final AcademicInformationAttachmentMapper academicInformationAttachmentMapper;

    private final AcademicInformationAttachmentSearchRepository academicInformationAttachmentSearchRepository;

    public AcademicInformationAttachmentQueryService(AcademicInformationAttachmentRepository academicInformationAttachmentRepository, AcademicInformationAttachmentMapper academicInformationAttachmentMapper, AcademicInformationAttachmentSearchRepository academicInformationAttachmentSearchRepository) {
        this.academicInformationAttachmentRepository = academicInformationAttachmentRepository;
        this.academicInformationAttachmentMapper = academicInformationAttachmentMapper;
        this.academicInformationAttachmentSearchRepository = academicInformationAttachmentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AcademicInformationAttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AcademicInformationAttachmentDTO> findByCriteria(AcademicInformationAttachmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AcademicInformationAttachment> specification = createSpecification(criteria);
        return academicInformationAttachmentMapper.toDto(academicInformationAttachmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AcademicInformationAttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AcademicInformationAttachmentDTO> findByCriteria(AcademicInformationAttachmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AcademicInformationAttachment> specification = createSpecification(criteria);
        return academicInformationAttachmentRepository.findAll(specification, page)
            .map(academicInformationAttachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AcademicInformationAttachmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AcademicInformationAttachment> specification = createSpecification(criteria);
        return academicInformationAttachmentRepository.count(specification);
    }

    /**
     * Function to convert AcademicInformationAttachmentCriteria to a {@link Specification}
     */
    private Specification<AcademicInformationAttachment> createSpecification(AcademicInformationAttachmentCriteria criteria) {
        Specification<AcademicInformationAttachment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AcademicInformationAttachment_.id));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(AcademicInformationAttachment_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
