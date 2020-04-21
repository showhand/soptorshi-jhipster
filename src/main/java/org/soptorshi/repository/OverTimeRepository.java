package org.soptorshi.repository;

import org.soptorshi.domain.OverTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OverTime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverTimeRepository extends JpaRepository<OverTime, Long>, JpaSpecificationExecutor<OverTime> {

}
