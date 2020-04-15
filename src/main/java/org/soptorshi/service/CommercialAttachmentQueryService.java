package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialAttachment;
import org.soptorshi.domain.CommercialAttachment_;
import org.soptorshi.domain.CommercialPi_;
import org.soptorshi.repository.CommercialAttachmentRepository;
import org.soptorshi.repository.search.CommercialAttachmentSearchRepository;
import org.soptorshi.service.dto.CommercialAttachmentCriteria;
import org.soptorshi.service.dto.CommercialAttachmentDTO;
import org.soptorshi.service.mapper.CommercialAttachmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for CommercialAttachment entities in the database.
 * The main input is a {@link CommercialAttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommercialAttachmentDTO} or a {@link Page} of {@link CommercialAttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommercialAttachmentQueryService extends QueryService<CommercialAttachment> {

    private final Logger log = LoggerFactory.getLogger(CommercialAttachmentQueryService.class);

    private final CommercialAttachmentRepository commercialAttachmentRepository;

    private final CommercialAttachmentMapper commercialAttachmentMapper;

    private final CommercialAttachmentSearchRepository commercialAttachmentSearchRepository;

    public CommercialAttachmentQueryService(CommercialAttachmentRepository commercialAttachmentRepository, CommercialAttachmentMapper commercialAttachmentMapper, CommercialAttachmentSearchRepository commercialAttachmentSearchRepository) {
        this.commercialAttachmentRepository = commercialAttachmentRepository;
        this.commercialAttachmentMapper = commercialAttachmentMapper;
        this.commercialAttachmentSearchRepository = commercialAttachmentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CommercialAttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommercialAttachmentDTO> findByCriteria(CommercialAttachmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommercialAttachment> specification = createSpecification(criteria);
        return commercialAttachmentMapper.toDto(commercialAttachmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommercialAttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommercialAttachmentDTO> findByCriteria(CommercialAttachmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommercialAttachment> specification = createSpecification(criteria);
        return commercialAttachmentRepository.findAll(specification, page)
            .map(commercialAttachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommercialAttachmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommercialAttachment> specification = createSpecification(criteria);
        return commercialAttachmentRepository.count(specification);
    }

    /**
     * Function to convert CommercialAttachmentCriteria to a {@link Specification}
     */
    private Specification<CommercialAttachment> createSpecification(CommercialAttachmentCriteria criteria) {
        Specification<CommercialAttachment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CommercialAttachment_.id));
            }
            if (criteria.getCommercialPiId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommercialPiId(),
                    root -> root.join(CommercialAttachment_.commercialPi, JoinType.LEFT).get(CommercialPi_.id)));
            }
        }
        return specification;
    }
}
