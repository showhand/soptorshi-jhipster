package org.soptorshi.repository;

import org.soptorshi.domain.PaymentVoucher;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaymentVoucher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentVoucherRepository extends JpaRepository<PaymentVoucher, Long>, JpaSpecificationExecutor<PaymentVoucher> {

}
