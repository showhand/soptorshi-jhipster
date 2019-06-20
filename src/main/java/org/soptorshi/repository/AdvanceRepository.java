package org.soptorshi.repository;

import org.soptorshi.domain.Advance;
import org.soptorshi.domain.Designation;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.PaymentStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Advance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvanceRepository extends JpaRepository<Advance, Long>, JpaSpecificationExecutor<Advance> {

    List<Advance> findByPaymentStatusAndEmployee_OfficeAndEmployee_DesignationAndEmployee_EmployeeStatus(PaymentStatus paymentStatus, Office office, Designation designation, EmployeeStatus employeeStatus);

    List<Advance> findByEmployeeAndPaymentStatus(Employee employee, PaymentStatus paymentStatus);

}
