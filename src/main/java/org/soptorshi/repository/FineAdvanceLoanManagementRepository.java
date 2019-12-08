package org.soptorshi.repository;

import org.soptorshi.domain.FineAdvanceLoanManagement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FineAdvanceLoanManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FineAdvanceLoanManagementRepository extends JpaRepository<FineAdvanceLoanManagement, Long> {

}
