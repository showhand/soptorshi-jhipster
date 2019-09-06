package org.soptorshi.repository;

import org.soptorshi.domain.MstAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MstAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MstAccountRepository extends JpaRepository<MstAccount, Long>, JpaSpecificationExecutor<MstAccount> {

}
