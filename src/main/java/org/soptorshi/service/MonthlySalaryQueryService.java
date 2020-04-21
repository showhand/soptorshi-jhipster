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

import org.soptorshi.domain.MonthlySalary;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.MonthlySalaryRepository;
import org.soptorshi.repository.search.MonthlySalarySearchRepository;
import org.soptorshi.service.dto.MonthlySalaryCriteria;
import org.soptorshi.service.dto.MonthlySalaryDTO;
import org.soptorshi.service.mapper.MonthlySalaryMapper;

/**
 * Service for executing complex queries for MonthlySalary entities in the database.
 * The main input is a {@link MonthlySalaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MonthlySalaryDTO} or a {@link Page} of {@link MonthlySalaryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MonthlySalaryQueryService extends QueryService<MonthlySalary> {

    private final Logger log = LoggerFactory.getLogger(MonthlySalaryQueryService.class);

    private final MonthlySalaryRepository monthlySalaryRepository;

    private final MonthlySalaryMapper monthlySalaryMapper;

    private final MonthlySalarySearchRepository monthlySalarySearchRepository;

    public MonthlySalaryQueryService(MonthlySalaryRepository monthlySalaryRepository, MonthlySalaryMapper monthlySalaryMapper, MonthlySalarySearchRepository monthlySalarySearchRepository) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.monthlySalaryMapper = monthlySalaryMapper;
        this.monthlySalarySearchRepository = monthlySalarySearchRepository;
    }

    /**
     * Return a {@link List} of {@link MonthlySalaryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MonthlySalaryDTO> findByCriteria(MonthlySalaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MonthlySalary> specification = createSpecification(criteria);
        return monthlySalaryMapper.toDto(monthlySalaryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MonthlySalaryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MonthlySalaryDTO> findByCriteria(MonthlySalaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MonthlySalary> specification = createSpecification(criteria);
        return monthlySalaryRepository.findAll(specification, page)
            .map(monthlySalaryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MonthlySalaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MonthlySalary> specification = createSpecification(criteria);
        return monthlySalaryRepository.count(specification);
    }

    /**
     * Function to convert MonthlySalaryCriteria to a {@link Specification}
     */
    private Specification<MonthlySalary> createSpecification(MonthlySalaryCriteria criteria) {
        Specification<MonthlySalary> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MonthlySalary_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), MonthlySalary_.year));
            }
            if (criteria.getMonth() != null) {
                specification = specification.and(buildSpecification(criteria.getMonth(), MonthlySalary_.month));
            }
            if (criteria.getBasic() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasic(), MonthlySalary_.basic));
            }
            if (criteria.getGross() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGross(), MonthlySalary_.gross));
            }
            if (criteria.getHouseRent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHouseRent(), MonthlySalary_.houseRent));
            }
            if (criteria.getMedicalAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMedicalAllowance(), MonthlySalary_.medicalAllowance));
            }
            if (criteria.getOverTimeAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOverTimeAllowance(), MonthlySalary_.overTimeAllowance));
            }
            if (criteria.getFoodAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFoodAllowance(), MonthlySalary_.foodAllowance));
            }
            if (criteria.getArrearAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArrearAllowance(), MonthlySalary_.arrearAllowance));
            }
            if (criteria.getDriverAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDriverAllowance(), MonthlySalary_.driverAllowance));
            }
            if (criteria.getFuelLubAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFuelLubAllowance(), MonthlySalary_.fuelLubAllowance));
            }
            if (criteria.getMobileAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMobileAllowance(), MonthlySalary_.mobileAllowance));
            }
            if (criteria.getTravelAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTravelAllowance(), MonthlySalary_.travelAllowance));
            }
            if (criteria.getOtherAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOtherAllowance(), MonthlySalary_.otherAllowance));
            }
            if (criteria.getFestivalAllowance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFestivalAllowance(), MonthlySalary_.festivalAllowance));
            }
            if (criteria.getAbsent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAbsent(), MonthlySalary_.absent));
            }
            if (criteria.getFine() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFine(), MonthlySalary_.fine));
            }
            if (criteria.getAdvanceHO() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdvanceHO(), MonthlySalary_.advanceHO));
            }
            if (criteria.getAdvanceFactory() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdvanceFactory(), MonthlySalary_.advanceFactory));
            }
            if (criteria.getProvidentFund() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProvidentFund(), MonthlySalary_.providentFund));
            }
            if (criteria.getTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTax(), MonthlySalary_.tax));
            }
            if (criteria.getLoanAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanAmount(), MonthlySalary_.loanAmount));
            }
            if (criteria.getBillPayable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillPayable(), MonthlySalary_.billPayable));
            }
            if (criteria.getBillReceivable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillReceivable(), MonthlySalary_.billReceivable));
            }
            if (criteria.getPayable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPayable(), MonthlySalary_.payable));
            }
            if (criteria.getApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getApproved(), MonthlySalary_.approved));
            }
            if (criteria.getOnHold() != null) {
                specification = specification.and(buildSpecification(criteria.getOnHold(), MonthlySalary_.onHold));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), MonthlySalary_.status));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), MonthlySalary_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), MonthlySalary_.modifiedOn));
            }
            if (criteria.getVoucherGenerated() != null) {
                specification = specification.and(buildSpecification(criteria.getVoucherGenerated(), MonthlySalary_.voucherGenerated));
            }
            if (criteria.getCommentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommentsId(),
                    root -> root.join(MonthlySalary_.comments, JoinType.LEFT).get(SalaryMessages_.id)));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(MonthlySalary_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
