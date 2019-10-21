package org.soptorshi.repository;

import org.soptorshi.domain.ContraVoucherGenerator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContraVoucherGenerator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContraVoucherGeneratorRepository extends JpaRepository<ContraVoucherGenerator, Long> {

}
