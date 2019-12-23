package org.soptorshi.repository;

import org.soptorshi.domain.MontlySalaryVouchers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MontlySalaryVouchers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MontlySalaryVouchersRepository extends JpaRepository<MontlySalaryVouchers, Long> {

}
