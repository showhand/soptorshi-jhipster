package org.soptorshi.repository;

import org.soptorshi.domain.DepreciationMap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepreciationMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepreciationMapRepository extends JpaRepository<DepreciationMap, Long>, JpaSpecificationExecutor<DepreciationMap> {

}
