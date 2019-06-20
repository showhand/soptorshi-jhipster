package org.soptorshi.repository;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Salary;
import org.soptorshi.domain.enumeration.SalaryStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Salary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long>, JpaSpecificationExecutor<Salary> {

    Salary getByEmployeeAndSalaryStatus(Employee employee, SalaryStatus salaryStatus);

}
