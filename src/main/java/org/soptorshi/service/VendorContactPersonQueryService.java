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

import org.soptorshi.domain.VendorContactPerson;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.VendorContactPersonRepository;
import org.soptorshi.repository.search.VendorContactPersonSearchRepository;
import org.soptorshi.service.dto.VendorContactPersonCriteria;
import org.soptorshi.service.dto.VendorContactPersonDTO;
import org.soptorshi.service.mapper.VendorContactPersonMapper;

/**
 * Service for executing complex queries for VendorContactPerson entities in the database.
 * The main input is a {@link VendorContactPersonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VendorContactPersonDTO} or a {@link Page} of {@link VendorContactPersonDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VendorContactPersonQueryService extends QueryService<VendorContactPerson> {

    private final Logger log = LoggerFactory.getLogger(VendorContactPersonQueryService.class);

    private final VendorContactPersonRepository vendorContactPersonRepository;

    private final VendorContactPersonMapper vendorContactPersonMapper;

    private final VendorContactPersonSearchRepository vendorContactPersonSearchRepository;

    public VendorContactPersonQueryService(VendorContactPersonRepository vendorContactPersonRepository, VendorContactPersonMapper vendorContactPersonMapper, VendorContactPersonSearchRepository vendorContactPersonSearchRepository) {
        this.vendorContactPersonRepository = vendorContactPersonRepository;
        this.vendorContactPersonMapper = vendorContactPersonMapper;
        this.vendorContactPersonSearchRepository = vendorContactPersonSearchRepository;
    }

    /**
     * Return a {@link List} of {@link VendorContactPersonDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VendorContactPersonDTO> findByCriteria(VendorContactPersonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VendorContactPerson> specification = createSpecification(criteria);
        return vendorContactPersonMapper.toDto(vendorContactPersonRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VendorContactPersonDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VendorContactPersonDTO> findByCriteria(VendorContactPersonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VendorContactPerson> specification = createSpecification(criteria);
        return vendorContactPersonRepository.findAll(specification, page)
            .map(vendorContactPersonMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VendorContactPersonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VendorContactPerson> specification = createSpecification(criteria);
        return vendorContactPersonRepository.count(specification);
    }

    /**
     * Function to convert VendorContactPersonCriteria to a {@link Specification}
     */
    private Specification<VendorContactPerson> createSpecification(VendorContactPersonCriteria criteria) {
        Specification<VendorContactPerson> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), VendorContactPerson_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), VendorContactPerson_.name));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), VendorContactPerson_.designation));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNumber(), VendorContactPerson_.contactNumber));
            }
            if (criteria.getVendorId() != null) {
                specification = specification.and(buildSpecification(criteria.getVendorId(),
                    root -> root.join(VendorContactPerson_.vendor, JoinType.LEFT).get(Vendor_.id)));
            }
        }
        return specification;
    }
}
