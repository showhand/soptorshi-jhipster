package org.soptorshi.repository;

import org.soptorshi.domain.CommercialPackaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialPackaging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialPackagingRepository extends JpaRepository<CommercialPackaging, Long>, JpaSpecificationExecutor<CommercialPackaging> {

}
