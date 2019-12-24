package org.soptorshi.repository;

import org.soptorshi.domain.CommercialPi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialPi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialPiRepository extends JpaRepository<CommercialPi, Long>, JpaSpecificationExecutor<CommercialPi> {

}
