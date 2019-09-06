package org.soptorshi.repository;

import org.soptorshi.domain.PeriodClose;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PeriodClose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodCloseRepository extends JpaRepository<PeriodClose, Long>, JpaSpecificationExecutor<PeriodClose> {

}
