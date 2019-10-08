package org.soptorshi.repository;

import org.soptorshi.domain.VoucherNumberGenerator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VoucherNumberGenerator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherNumberGeneratorRepository extends JpaRepository<VoucherNumberGenerator, Long> {

}
