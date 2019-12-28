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

import org.soptorshi.domain.RequisitionMessages;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.RequisitionMessagesRepository;
import org.soptorshi.repository.search.RequisitionMessagesSearchRepository;
import org.soptorshi.service.dto.RequisitionMessagesCriteria;
import org.soptorshi.service.dto.RequisitionMessagesDTO;
import org.soptorshi.service.mapper.RequisitionMessagesMapper;

/**
 * Service for executing complex queries for RequisitionMessages entities in the database.
 * The main input is a {@link RequisitionMessagesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RequisitionMessagesDTO} or a {@link Page} of {@link RequisitionMessagesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RequisitionMessagesQueryService extends QueryService<RequisitionMessages> {

    private final Logger log = LoggerFactory.getLogger(RequisitionMessagesQueryService.class);

    private final RequisitionMessagesRepository requisitionMessagesRepository;

    private final RequisitionMessagesMapper requisitionMessagesMapper;

    private final RequisitionMessagesSearchRepository requisitionMessagesSearchRepository;

    public RequisitionMessagesQueryService(RequisitionMessagesRepository requisitionMessagesRepository, RequisitionMessagesMapper requisitionMessagesMapper, RequisitionMessagesSearchRepository requisitionMessagesSearchRepository) {
        this.requisitionMessagesRepository = requisitionMessagesRepository;
        this.requisitionMessagesMapper = requisitionMessagesMapper;
        this.requisitionMessagesSearchRepository = requisitionMessagesSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RequisitionMessagesDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RequisitionMessagesDTO> findByCriteria(RequisitionMessagesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RequisitionMessages> specification = createSpecification(criteria);
        return requisitionMessagesMapper.toDto(requisitionMessagesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RequisitionMessagesDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RequisitionMessagesDTO> findByCriteria(RequisitionMessagesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RequisitionMessages> specification = createSpecification(criteria);
        return requisitionMessagesRepository.findAll(specification, page)
            .map(requisitionMessagesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RequisitionMessagesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RequisitionMessages> specification = createSpecification(criteria);
        return requisitionMessagesRepository.count(specification);
    }

    /**
     * Function to convert RequisitionMessagesCriteria to a {@link Specification}
     */
    private Specification<RequisitionMessages> createSpecification(RequisitionMessagesCriteria criteria) {
        Specification<RequisitionMessages> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RequisitionMessages_.id));
            }
            if (criteria.getCommentedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommentedOn(), RequisitionMessages_.commentedOn));
            }
            if (criteria.getCommenterId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommenterId(),
                    root -> root.join(RequisitionMessages_.commenter, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getRequisitionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionId(),
                    root -> root.join(RequisitionMessages_.requisition, JoinType.LEFT).get(Requisition_.id)));
            }
        }
        return specification;
    }
}
