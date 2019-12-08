package org.soptorshi.repository;

import org.soptorshi.domain.PayrollManagement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PayrollManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollManagementRepository extends JpaRepository<PayrollManagement, Long> {

}
