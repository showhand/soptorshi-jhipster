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

import org.soptorshi.domain.FamilyInformation;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.FamilyInformationRepository;
import org.soptorshi.repository.search.FamilyInformationSearchRepository;
import org.soptorshi.service.dto.FamilyInformationCriteria;
import org.soptorshi.service.dto.FamilyInformationDTO;
import org.soptorshi.service.mapper.FamilyInformationMapper;

/**
 * Service for executing complex queries for FamilyInformation entities in the database.
 * The main input is a {@link FamilyInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FamilyInformationDTO} or a {@link Page} of {@link FamilyInformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FamilyInformationQueryService extends QueryService<FamilyInformation> {

    private final Logger log = LoggerFactory.getLogger(FamilyInformationQueryService.class);

    private final FamilyInformationRepository familyInformationRepository;

    private final FamilyInformationMapper familyInformationMapper;

    private final FamilyInformationSearchRepository familyInformationSearchRepository;

    public FamilyInformationQueryService(FamilyInformationRepository familyInformationRepository, FamilyInformationMapper familyInformationMapper, FamilyInformationSearchRepository familyInformationSearchRepository) {
        this.familyInformationRepository = familyInformationRepository;
        this.familyInformationMapper = familyInformationMapper;
        this.familyInformationSearchRepository = familyInformationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FamilyInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FamilyInformationDTO> findByCriteria(FamilyInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FamilyInformation> specification = createSpecification(criteria);
        return familyInformationMapper.toDto(familyInformationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FamilyInformationDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FamilyInformationDTO> findByCriteria(FamilyInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FamilyInformation> specification = createSpecification(criteria);
        return familyInformationRepository.findAll(specification, page)
            .map(familyInformationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FamilyInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FamilyInformation> specification = createSpecification(criteria);
        return familyInformationRepository.count(specification);
    }

    /**
     * Function to convert FamilyInformationCriteria to a {@link Specification}
     */
    private Specification<FamilyInformation> createSpecification(FamilyInformationCriteria criteria) {
        Specification<FamilyInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FamilyInformation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), FamilyInformation_.name));
            }
            if (criteria.getRelation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelation(), FamilyInformation_.relation));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNumber(), FamilyInformation_.contactNumber));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(FamilyInformation_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
