package org.soptorshi.repository;

import org.soptorshi.domain.VoucherNumberControl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VoucherNumberControl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherNumberControlRepository extends JpaRepository<VoucherNumberControl, Long>, JpaSpecificationExecutor<VoucherNumberControl> {

}
