package org.soptorshi.repository;

import org.soptorshi.domain.ChequeRegister;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChequeRegister entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChequeRegisterRepository extends JpaRepository<ChequeRegister, Long>, JpaSpecificationExecutor<ChequeRegister> {

}
