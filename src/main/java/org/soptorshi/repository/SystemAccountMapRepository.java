package org.soptorshi.repository;

import org.soptorshi.domain.SystemAccountMap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SystemAccountMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemAccountMapRepository extends JpaRepository<SystemAccountMap, Long>, JpaSpecificationExecutor<SystemAccountMap> {

}
