package org.soptorshi.repository;

import org.soptorshi.domain.SupplyZoneWiseAccumulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyZoneWiseAccumulation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyZoneWiseAccumulationRepository extends JpaRepository<SupplyZoneWiseAccumulation, Long>, JpaSpecificationExecutor<SupplyZoneWiseAccumulation> {

}
