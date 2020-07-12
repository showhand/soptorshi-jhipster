package org.soptorshi.repository.extended;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.SupplyZoneManagerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the SupplyZoneManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyZoneManagerExtendedRepository extends SupplyZoneManagerRepository {

    List<SupplyZoneManager> getAllByEmployee(Employee employee);

    List<SupplyZoneManager> getAllByEmployeeAndStatus(Employee employee, SupplyZoneManagerStatus supplyZoneManagerStatus);
}
