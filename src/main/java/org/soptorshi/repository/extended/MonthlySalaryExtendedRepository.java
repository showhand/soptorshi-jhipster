package org.soptorshi.repository.extended;

import org.soptorshi.domain.MonthlySalary;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.MonthlySalaryStatus;
import org.soptorshi.domain.enumeration.SalaryStatus;
import org.soptorshi.repository.MonthlySalaryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlySalaryExtendedRepository extends MonthlySalaryRepository {

    void deleteAllByYearAndMonthAndEmployee_Office_Id(Integer year, MonthType monthType, Long officeId);

    void deleteAllByYearAndMonthAndEmployee_Office_IdAndEmployee_Designation_Id(Integer year, MonthType monthType, Long officeId, Long designationId);

    void deleteAllByYearAndMonthAndEmployee_Office_IdAndEmployee_idAndStatusIsNot(Integer year, MonthType monthType, Long officeId, Long employeeId, MonthlySalaryStatus status);

    void deleteByYearAndMonthAndEmployee_Office_IdAndStatusIsNot(Integer year, MonthType monthType, Long officeId, MonthlySalaryStatus status);

    List<MonthlySalary> getByEmployee_Office_IdAndYearAndMonth(long officeId, int year, MonthType monthType);

    List<MonthlySalary> getByEmployee_Office_IdAndEmployee_Designation_IdAndYearAndMonth(Long officeId, Long designationId, Integer year, MonthType monthType);
}
