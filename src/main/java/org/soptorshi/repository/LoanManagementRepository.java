package org.soptorshi.repository;

import org.soptorshi.domain.LoanManagement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LoanManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanManagementRepository extends JpaRepository<LoanManagement, Long> {

}
