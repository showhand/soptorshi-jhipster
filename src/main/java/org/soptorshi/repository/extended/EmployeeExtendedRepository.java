package org.soptorshi.repository.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeExtendedRepository extends EmployeeRepository {
    List<Employee> getByOffice_IdAndDesignation_IdAndEmployeeStatus(Long officeId, Long designationId, EmployeeStatus employeeStatus);

    Optional<Employee> findByEmployeeId(String employeeId);

}
