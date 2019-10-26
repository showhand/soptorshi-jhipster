package org.soptorshi.repository;

import org.soptorshi.domain.ReceiptVoucher;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReceiptVoucher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceiptVoucherRepository extends JpaRepository<ReceiptVoucher, Long>, JpaSpecificationExecutor<ReceiptVoucher> {

}
