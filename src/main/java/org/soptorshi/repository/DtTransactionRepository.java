package org.soptorshi.repository;

import org.soptorshi.domain.DtTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DtTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DtTransactionRepository extends JpaRepository<DtTransaction, Long>, JpaSpecificationExecutor<DtTransaction> {

}
