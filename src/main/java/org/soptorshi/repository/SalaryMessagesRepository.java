package org.soptorshi.repository;

import org.soptorshi.domain.SalaryMessages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SalaryMessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalaryMessagesRepository extends JpaRepository<SalaryMessages, Long>, JpaSpecificationExecutor<SalaryMessages> {

}
