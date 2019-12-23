package org.soptorshi.repository;

import org.soptorshi.domain.CommercialPoStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialPoStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialPoStatusRepository extends JpaRepository<CommercialPoStatus, Long>, JpaSpecificationExecutor<CommercialPoStatus> {

}
