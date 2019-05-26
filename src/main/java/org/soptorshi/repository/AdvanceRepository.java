package org.soptorshi.repository;

import org.soptorshi.domain.Advance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Advance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvanceRepository extends JpaRepository<Advance, Long>, JpaSpecificationExecutor<Advance> {

}
