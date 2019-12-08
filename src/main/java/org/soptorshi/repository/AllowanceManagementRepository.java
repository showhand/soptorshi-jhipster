package org.soptorshi.repository;

import org.soptorshi.domain.AllowanceManagement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AllowanceManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllowanceManagementRepository extends JpaRepository<AllowanceManagement, Long> {

}
