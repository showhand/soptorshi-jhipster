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

import org.soptorshi.domain.DepartmentHead;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.DepartmentHeadRepository;
import org.soptorshi.repository.search.DepartmentHeadSearchRepository;
import org.soptorshi.service.dto.DepartmentHeadCriteria;
import org.soptorshi.service.dto.DepartmentHeadDTO;
import org.soptorshi.service.mapper.DepartmentHeadMapper;

/**
 * Service for executing complex queries for DepartmentHead entities in the database.
 * The main input is a {@link DepartmentHeadCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DepartmentHeadDTO} or a {@link Page} of {@link DepartmentHeadDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepartmentHeadQueryService extends QueryService<DepartmentHead> {

    private final Logger log = LoggerFactory.getLogger(DepartmentHeadQueryService.class);

    private final DepartmentHeadRepository departmentHeadRepository;

    private final DepartmentHeadMapper departmentHeadMapper;

    private final DepartmentHeadSearchRepository departmentHeadSearchRepository;

    public DepartmentHeadQueryService(DepartmentHeadRepository departmentHeadRepository, DepartmentHeadMapper departmentHeadMapper, DepartmentHeadSearchRepository departmentHeadSearchRepository) {
        this.departmentHeadRepository = departmentHeadRepository;
        this.departmentHeadMapper = departmentHeadMapper;
        this.departmentHeadSearchRepository = departmentHeadSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DepartmentHeadDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DepartmentHeadDTO> findByCriteria(DepartmentHeadCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DepartmentHead> specification = createSpecification(criteria);
        return departmentHeadMapper.toDto(departmentHeadRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DepartmentHeadDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartmentHeadDTO> findByCriteria(DepartmentHeadCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DepartmentHead> specification = createSpecification(criteria);
        return departmentHeadRepository.findAll(specification, page)
            .map(departmentHeadMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepartmentHeadCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DepartmentHead> specification = createSpecification(criteria);
        return departmentHeadRepository.count(specification);
    }

    /**
     * Function to convert DepartmentHeadCriteria to a {@link Specification}
     */
    private Specification<DepartmentHead> createSpecification(DepartmentHeadCriteria criteria) {
        Specification<DepartmentHead> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DepartmentHead_.id));
            }
            if (criteria.getOfficeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfficeId(),
                    root -> root.join(DepartmentHead_.office, JoinType.LEFT).get(Office_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(DepartmentHead_.department, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(DepartmentHead_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
