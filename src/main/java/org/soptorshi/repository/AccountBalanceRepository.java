package org.soptorshi.repository;

import org.soptorshi.domain.AccountBalance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountBalance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long>, JpaSpecificationExecutor<AccountBalance> {

}
