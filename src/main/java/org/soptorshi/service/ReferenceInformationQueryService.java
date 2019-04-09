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

import org.soptorshi.domain.ReferenceInformation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ReferenceInformationRepository;
import org.soptorshi.repository.search.ReferenceInformationSearchRepository;
import org.soptorshi.service.dto.ReferenceInformationCriteria;
import org.soptorshi.service.dto.ReferenceInformationDTO;
import org.soptorshi.service.mapper.ReferenceInformationMapper;

/**
 * Service for executing complex queries for ReferenceInformation entities in the database.
 * The main input is a {@link ReferenceInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReferenceInformationDTO} or a {@link Page} of {@link ReferenceInformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReferenceInformationQueryService extends QueryService<ReferenceInformation> {

    private final Logger log = LoggerFactory.getLogger(ReferenceInformationQueryService.class);

    private final ReferenceInformationRepository referenceInformationRepository;

    private final ReferenceInformationMapper referenceInformationMapper;

    private final ReferenceInformationSearchRepository referenceInformationSearchRepository;

    public ReferenceInformationQueryService(ReferenceInformationRepository referenceInformationRepository, ReferenceInformationMapper referenceInformationMapper, ReferenceInformationSearchRepository referenceInformationSearchRepository) {
        this.referenceInformationRepository = referenceInformationRepository;
        this.referenceInformationMapper = referenceInformationMapper;
        this.referenceInformationSearchRepository = referenceInformationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ReferenceInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReferenceInformationDTO> findByCriteria(ReferenceInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ReferenceInformation> specification = createSpecification(criteria);
        return referenceInformationMapper.toDto(referenceInformationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReferenceInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferenceInformationDTO> findByCriteria(ReferenceInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ReferenceInformation> specification = createSpecification(criteria);
        return referenceInformationRepository.findAll(specification, page)
            .map(referenceInformationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReferenceInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ReferenceInformation> specification = createSpecification(criteria);
        return referenceInformationRepository.count(specification);
    }

    /**
     * Function to convert ReferenceInformationCriteria to a {@link Specification}
     */
    private Specification<ReferenceInformation> createSpecification(ReferenceInformationCriteria criteria) {
        Specification<ReferenceInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ReferenceInformation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ReferenceInformation_.name));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), ReferenceInformation_.designation));
            }
            if (criteria.getOrganization() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganization(), ReferenceInformation_.organization));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNumber(), ReferenceInformation_.contactNumber));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(ReferenceInformation_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
