package org.soptorshi.repository;

import org.soptorshi.domain.SupplyZoneManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyZoneManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyZoneManagerRepository extends JpaRepository<SupplyZoneManager, Long>, JpaSpecificationExecutor<SupplyZoneManager> {

}
