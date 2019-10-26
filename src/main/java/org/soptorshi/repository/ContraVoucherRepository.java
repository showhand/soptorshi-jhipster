package org.soptorshi.repository;

import org.soptorshi.domain.ContraVoucher;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContraVoucher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContraVoucherRepository extends JpaRepository<ContraVoucher, Long>, JpaSpecificationExecutor<ContraVoucher> {

}
