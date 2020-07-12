package org.soptorshi.repository.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;
import org.soptorshi.repository.SupplyAreaManagerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the SupplyAreaManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyAreaManagerExtendedRepository extends SupplyAreaManagerRepository {

    List<SupplyAreaManager> getAllByEmployee(Employee employee);

    List<SupplyAreaManager> getAllByEmployeeAndStatus(Employee employee, SupplyAreaManagerStatus supplyAreaManagerStatus);
}
