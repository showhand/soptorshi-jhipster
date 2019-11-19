package org.soptorshi.repository.extended;

import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.repository.MonthlySalaryRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlySalaryExtendedRepository extends MonthlySalaryRepository {
    void deleteAllByYearAndMonthAndEmployee_Office_IdAndEmployee_Designation_Id(Integer year, MonthType monthType, Long officeId, Long designationId);
}
