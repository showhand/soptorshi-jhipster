package org.soptorshi.repository;

import org.soptorshi.domain.DesignationWiseAllowance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DesignationWiseAllowance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignationWiseAllowanceRepository extends JpaRepository<DesignationWiseAllowance, Long>, JpaSpecificationExecutor<DesignationWiseAllowance> {

}
