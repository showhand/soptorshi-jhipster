package org.soptorshi.repository;

import org.soptorshi.domain.VendorContactPerson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VendorContactPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VendorContactPersonRepository extends JpaRepository<VendorContactPerson, Long>, JpaSpecificationExecutor<VendorContactPerson> {

}
