package org.soptorshi.repository;

import org.soptorshi.domain.Designation;
import org.soptorshi.domain.Fine;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Fine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FineRepository extends JpaRepository<Fine, Long>, JpaSpecificationExecutor<Fine> {
    List<Fine> findByEmployee_OfficeAndEmployee_DesignationAAndEmployee_EmployeeStatus(Office office, Designation designation, EmployeeStatus employeeStatus);
}
