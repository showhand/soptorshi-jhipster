package org.soptorshi.repository;

import org.soptorshi.domain.Designation;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    List<Employee> getByOffice_IdAndDesignation_IdAndEmployeeStatus(Long officeId, Long designationId, EmployeeStatus employeeStatus);

    Optional<Employee> findByEmployeeId(String employeeId);

    List<Employee> getByOffice_IdAndEmployeeStatus(Long officeId, EmployeeStatus status);
}
