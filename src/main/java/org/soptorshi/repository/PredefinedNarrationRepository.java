package org.soptorshi.repository;

import org.soptorshi.domain.PredefinedNarration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PredefinedNarration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PredefinedNarrationRepository extends JpaRepository<PredefinedNarration, Long>, JpaSpecificationExecutor<PredefinedNarration> {

}
