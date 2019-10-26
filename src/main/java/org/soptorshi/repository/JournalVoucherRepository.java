package org.soptorshi.repository;

import org.soptorshi.domain.JournalVoucher;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JournalVoucher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JournalVoucherRepository extends JpaRepository<JournalVoucher, Long>, JpaSpecificationExecutor<JournalVoucher> {

}
