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

import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.RequisitionRepository;
import org.soptorshi.repository.search.RequisitionSearchRepository;
import org.soptorshi.service.dto.RequisitionCriteria;
import org.soptorshi.service.dto.RequisitionDTO;
import org.soptorshi.service.mapper.RequisitionMapper;

/**
 * Service for executing complex queries for Requisition entities in the database.
 * The main input is a {@link RequisitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RequisitionDTO} or a {@link Page} of {@link RequisitionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RequisitionQueryService extends QueryService<Requisition> {

    private final Logger log = LoggerFactory.getLogger(RequisitionQueryService.class);

    private final RequisitionRepository requisitionRepository;

    private final RequisitionMapper requisitionMapper;

    private final RequisitionSearchRepository requisitionSearchRepository;

    public RequisitionQueryService(RequisitionRepository requisitionRepository, RequisitionMapper requisitionMapper, RequisitionSearchRepository requisitionSearchRepository) {
        this.requisitionRepository = requisitionRepository;
        this.requisitionMapper = requisitionMapper;
        this.requisitionSearchRepository = requisitionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RequisitionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RequisitionDTO> findByCriteria(RequisitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Requisition> specification = createSpecification(criteria);
        return requisitionMapper.toDto(requisitionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RequisitionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RequisitionDTO> findByCriteria(RequisitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Requisition> specification = createSpecification(criteria);
        return requisitionRepository.findAll(specification, page)
            .map(requisitionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RequisitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Requisition> specification = createSpecification(criteria);
        return requisitionRepository.count(specification);
    }

    /**
     * Function to convert RequisitionCriteria to a {@link Specification}
     */
    private Specification<Requisition> createSpecification(RequisitionCriteria criteria) {
        Specification<Requisition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Requisition_.id));
            }
            if (criteria.getRequisitionNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequisitionNo(), Requisition_.requisitionNo));
            }
            if (criteria.getRequisitionType() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionType(), Requisition_.requisitionType));
            }
            if (criteria.getRequisitionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequisitionDate(), Requisition_.requisitionDate));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Requisition_.amount));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Requisition_.status));
            }
            if (criteria.getSelected() != null) {
                specification = specification.and(buildSpecification(criteria.getSelected(), Requisition_.selected));
            }
            if (criteria.getRefToHead() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefToHead(), Requisition_.refToHead));
            }
            if (criteria.getRefToPurchaseCommittee() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefToPurchaseCommittee(), Requisition_.refToPurchaseCommittee));
            }
            if (criteria.getRefToCfo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRefToCfo(), Requisition_.refToCfo));
            }
            if (criteria.getCommercialId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommercialId(), Requisition_.commercialId));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Requisition_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), Requisition_.modifiedOn));
            }
            if (criteria.getCommentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommentsId(),
                    root -> root.join(Requisition_.comments, JoinType.LEFT).get(RequisitionMessages_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Requisition_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getOfficeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfficeId(),
                    root -> root.join(Requisition_.office, JoinType.LEFT).get(Office_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(Requisition_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(Requisition_.department, JoinType.LEFT).get(Department_.id)));
            }
        }
        return specification;
    }
}
