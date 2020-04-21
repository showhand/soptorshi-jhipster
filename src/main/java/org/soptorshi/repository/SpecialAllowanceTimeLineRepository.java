package org.soptorshi.repository;

import org.soptorshi.domain.SpecialAllowanceTimeLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SpecialAllowanceTimeLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialAllowanceTimeLineRepository extends JpaRepository<SpecialAllowanceTimeLine, Long>, JpaSpecificationExecutor<SpecialAllowanceTimeLine> {

}
