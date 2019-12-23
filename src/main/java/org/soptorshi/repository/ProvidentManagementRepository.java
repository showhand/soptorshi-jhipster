package org.soptorshi.repository;

import org.soptorshi.domain.ProvidentManagement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProvidentManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProvidentManagementRepository extends JpaRepository<ProvidentManagement, Long> {

}
