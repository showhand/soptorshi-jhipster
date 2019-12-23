package org.soptorshi.repository;

import org.soptorshi.domain.AdvanceManagement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdvanceManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdvanceManagementRepository extends JpaRepository<AdvanceManagement, Long> {

}
