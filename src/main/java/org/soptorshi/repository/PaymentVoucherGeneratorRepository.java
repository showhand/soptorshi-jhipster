package org.soptorshi.repository;

import org.soptorshi.domain.PaymentVoucherGenerator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaymentVoucherGenerator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentVoucherGeneratorRepository extends JpaRepository<PaymentVoucherGenerator, Long> {

}
