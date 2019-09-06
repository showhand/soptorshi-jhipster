package org.soptorshi.repository;

import org.soptorshi.domain.DebtorLedger;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DebtorLedger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DebtorLedgerRepository extends JpaRepository<DebtorLedger, Long>, JpaSpecificationExecutor<DebtorLedger> {

}
