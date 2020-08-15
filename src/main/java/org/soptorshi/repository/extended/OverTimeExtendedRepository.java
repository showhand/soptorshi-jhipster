package org.soptorshi.repository.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.OverTime;
import org.soptorshi.repository.OverTimeRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the OverTime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverTimeExtendedRepository extends OverTimeRepository {

    List<OverTime> getAllByOverTimeDateGreaterThanEqualAndOverTimeDateIsLessThanEqualAndEmployeeEqualsOrderByOverTimeDateDesc(LocalDate from, LocalDate to, Employee employee);

    List<OverTime> getAllByOverTimeDateGreaterThanEqualAndOverTimeDateIsLessThanEqualOrderByOverTimeDateDesc(LocalDate from, LocalDate to);

    List<OverTime> getAllByEmployeeEqualsOrderByOverTimeDateDesc(Employee employee);

}
