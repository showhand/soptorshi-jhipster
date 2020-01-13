package org.soptorshi.repository;

import org.soptorshi.domain.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Production entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductionRepository extends JpaRepository<Production, Long>, JpaSpecificationExecutor<Production> {

}
