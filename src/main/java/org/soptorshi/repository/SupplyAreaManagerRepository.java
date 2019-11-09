package org.soptorshi.repository;

import org.soptorshi.domain.SupplyAreaManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyAreaManager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyAreaManagerRepository extends JpaRepository<SupplyAreaManager, Long>, JpaSpecificationExecutor<SupplyAreaManager> {

}
