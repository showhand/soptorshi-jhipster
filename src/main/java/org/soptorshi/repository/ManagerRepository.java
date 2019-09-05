package org.soptorshi.repository;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Manager;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Manager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>, JpaSpecificationExecutor<Manager> {

    Optional<Manager> getByParentEmployeeIdAndEmployee(Long parentEmployeeId, Employee employee);
}
