package org.soptorshi.repository;

import org.soptorshi.domain.Designation;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Loan;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.PaymentStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Loan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    List<Loan> getByPaymentStatusAndEmployee_OfficeAndEmployee_DesignationAndEmployee_EmployeeStatus(PaymentStatus paymentStatus, Office office, Designation designation, EmployeeStatus employeeStatus);

    List<Loan> getByEmployeeAndPaymentStatus(Employee employee, PaymentStatus paymentStatus);

}
