package org.soptorshi.repository;

import org.soptorshi.domain.DepreciationCalculation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepreciationCalculation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepreciationCalculationRepository extends JpaRepository<DepreciationCalculation, Long>, JpaSpecificationExecutor<DepreciationCalculation> {

}
