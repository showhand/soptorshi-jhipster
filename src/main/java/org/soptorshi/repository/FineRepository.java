package org.soptorshi.repository;

import org.soptorshi.domain.Designation;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Fine;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.PaymentStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Fine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FineRepository extends JpaRepository<Fine, Long>, JpaSpecificationExecutor<Fine> {
    List<Fine> findByPaymentStatusAndEmployee_Office_IdAndEmployee_Designation_IdAndEmployee_EmployeeStatus(PaymentStatus paymentStaus, Long officeId, Long designationId, EmployeeStatus employeeStatus);

    List<Fine> findByEmployee_IdAndPaymentStatus(Long employeeId, PaymentStatus paymentStatus);
}
