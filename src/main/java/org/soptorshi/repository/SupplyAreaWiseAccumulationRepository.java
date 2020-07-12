package org.soptorshi.repository;

import org.soptorshi.domain.SupplyAreaWiseAccumulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyAreaWiseAccumulation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyAreaWiseAccumulationRepository extends JpaRepository<SupplyAreaWiseAccumulation, Long>, JpaSpecificationExecutor<SupplyAreaWiseAccumulation> {

}
