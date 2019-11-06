package org.soptorshi.repository;

import org.soptorshi.domain.SupplyChallan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyChallan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyChallanRepository extends JpaRepository<SupplyChallan, Long>, JpaSpecificationExecutor<SupplyChallan> {

}
