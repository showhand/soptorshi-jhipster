package org.soptorshi.repository;

import org.soptorshi.domain.MstGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MstGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MstGroupRepository extends JpaRepository<MstGroup, Long>, JpaSpecificationExecutor<MstGroup> {

}
