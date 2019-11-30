package org.soptorshi.repository.extended;

import org.soptorshi.domain.MonthlySalary;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.repository.MonthlySalaryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlySalaryExtendedRepository extends MonthlySalaryRepository {
    void deleteAllByYearAndMonthAndEmployee_Office_IdAndEmployee_Designation_Id(Integer year, MonthType monthType, Long officeId, Long designationId);

    void deleteAllByYearAndMonthAndEmployee_Office_IdAndEmployee_Designation_IdAndEmployee_id(Integer year, MonthType monthType, Long officeId, Long designationId, Long employeeId);

    List<MonthlySalary> getByEmployee_Office_IdAndYearAndMonth(long officeId, int year, MonthType monthType);
}
