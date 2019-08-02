package org.soptorshi.repository;

import org.soptorshi.domain.Manufacturer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Manufacturer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>, JpaSpecificationExecutor<Manufacturer> {

}
