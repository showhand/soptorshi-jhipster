package org.soptorshi.repository;

import org.soptorshi.domain.InventorySubLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InventorySubLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventorySubLocationRepository extends JpaRepository<InventorySubLocation, Long>, JpaSpecificationExecutor<InventorySubLocation> {

}
