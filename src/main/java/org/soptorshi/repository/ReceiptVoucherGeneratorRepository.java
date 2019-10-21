package org.soptorshi.repository;

import org.soptorshi.domain.ReceiptVoucherGenerator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReceiptVoucherGenerator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceiptVoucherGeneratorRepository extends JpaRepository<ReceiptVoucherGenerator, Long> {

}
