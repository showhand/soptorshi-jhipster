package org.soptorshi.repository;

import org.soptorshi.domain.SupplyArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyArea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyAreaRepository extends JpaRepository<SupplyArea, Long>, JpaSpecificationExecutor<SupplyArea> {

}
