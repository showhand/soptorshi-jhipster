package org.soptorshi.repository;

import org.soptorshi.domain.Weekend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Weekend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeekendRepository extends JpaRepository<Weekend, Long>, JpaSpecificationExecutor<Weekend> {

}
