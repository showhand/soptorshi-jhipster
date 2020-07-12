package org.soptorshi.repository;

import org.soptorshi.domain.SupplyZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyZone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyZoneRepository extends JpaRepository<SupplyZone, Long>, JpaSpecificationExecutor<SupplyZone> {

}
