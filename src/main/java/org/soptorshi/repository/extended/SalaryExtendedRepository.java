package org.soptorshi.repository.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Salary;
import org.soptorshi.domain.enumeration.SalaryStatus;
import org.soptorshi.repository.SalaryRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryExtendedRepository extends SalaryRepository {
    Salary getByEmployeeAndSalaryStatus(Employee employee, SalaryStatus salaryStatus);
}
