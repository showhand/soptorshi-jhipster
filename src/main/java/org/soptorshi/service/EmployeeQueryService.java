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

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.EmployeeRepository;
import org.soptorshi.repository.search.EmployeeSearchRepository;
import org.soptorshi.service.dto.EmployeeCriteria;
import org.soptorshi.service.dto.EmployeeDTO;
import org.soptorshi.service.mapper.EmployeeMapper;

/**
 * Service for executing complex queries for Employee entities in the database.
 * The main input is a {@link EmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDTO} or a {@link Page} of {@link EmployeeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService extends QueryService<Employee> {

    private final Logger log = LoggerFactory.getLogger(EmployeeQueryService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final EmployeeSearchRepository employeeSearchRepository;

    public EmployeeQueryService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, EmployeeSearchRepository employeeSearchRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.employeeSearchRepository = employeeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByCriteria(EmployeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeMapper.toDto(employeeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findByCriteria(EmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification, page)
            .map(employeeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.count(specification);
    }

    /**
     * Function to convert EmployeeCriteria to a {@link Specification}
     */
    private Specification<Employee> createSpecification(EmployeeCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Employee_.id));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeId(), Employee_.employeeId));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), Employee_.fullName));
            }
            if (criteria.getFathersName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFathersName(), Employee_.fathersName));
            }
            if (criteria.getMothersName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMothersName(), Employee_.mothersName));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), Employee_.birthDate));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getMaritalStatus(), Employee_.maritalStatus));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Employee_.gender));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildSpecification(criteria.getReligion(), Employee_.religion));
            }
            if (criteria.getPermanentAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermanentAddress(), Employee_.permanentAddress));
            }
            if (criteria.getPresentAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPresentAddress(), Employee_.presentAddress));
            }
            if (criteria.getnId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnId(), Employee_.nId));
            }
            if (criteria.getTin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTin(), Employee_.tin));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNumber(), Employee_.contactNumber));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Employee_.email));
            }
            if (criteria.getBloodGroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBloodGroup(), Employee_.bloodGroup));
            }
            if (criteria.getEmergencyContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmergencyContact(), Employee_.emergencyContact));
            }
            if (criteria.getJoiningDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJoiningDate(), Employee_.joiningDate));
            }
            if (criteria.getManager() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getManager(), Employee_.manager));
            }
            if (criteria.getEmployeeStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeStatus(), Employee_.employeeStatus));
            }
            if (criteria.getEmploymentType() != null) {
                specification = specification.and(buildSpecification(criteria.getEmploymentType(), Employee_.employmentType));
            }
            if (criteria.getTerminationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTerminationDate(), Employee_.terminationDate));
            }
            if (criteria.getReasonOfTermination() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReasonOfTermination(), Employee_.reasonOfTermination));
            }
            if (criteria.getUserAccount() != null) {
                specification = specification.and(buildSpecification(criteria.getUserAccount(), Employee_.userAccount));
            }
            if (criteria.getHourlySalary() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHourlySalary(), Employee_.hourlySalary));
            }
            if (criteria.getBankAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccountNo(), Employee_.bankAccountNo));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), Employee_.bankName));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(Employee_.department, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getOfficeId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfficeId(),
                    root -> root.join(Employee_.office, JoinType.LEFT).get(Office_.id)));
            }
            if (criteria.getDesignationId() != null) {
                specification = specification.and(buildSpecification(criteria.getDesignationId(),
                    root -> root.join(Employee_.designation, JoinType.LEFT).get(Designation_.id)));
            }
        }
        return specification;
    }
}
