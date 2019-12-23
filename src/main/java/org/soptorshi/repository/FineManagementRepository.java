package org.soptorshi.repository;

import org.soptorshi.domain.FineManagement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FineManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FineManagementRepository extends JpaRepository<FineManagement, Long> {

}
