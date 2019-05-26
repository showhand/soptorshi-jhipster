package org.soptorshi.repository;

import org.soptorshi.domain.Fine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FineRepository extends JpaRepository<Fine, Long>, JpaSpecificationExecutor<Fine> {

}
