package org.soptorshi.repository;

import org.soptorshi.domain.SystemGroupMap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SystemGroupMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemGroupMapRepository extends JpaRepository<SystemGroupMap, Long>, JpaSpecificationExecutor<SystemGroupMap> {

}
