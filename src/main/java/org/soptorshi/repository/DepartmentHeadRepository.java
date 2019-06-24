package org.soptorshi.repository;

import org.soptorshi.domain.DepartmentHead;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepartmentHead entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentHeadRepository extends JpaRepository<DepartmentHead, Long>, JpaSpecificationExecutor<DepartmentHead> {

}
