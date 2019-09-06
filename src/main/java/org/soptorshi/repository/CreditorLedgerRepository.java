package org.soptorshi.repository;

import org.soptorshi.domain.CreditorLedger;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CreditorLedger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditorLedgerRepository extends JpaRepository<CreditorLedger, Long>, JpaSpecificationExecutor<CreditorLedger> {

}
