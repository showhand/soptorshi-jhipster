package org.soptorshi.repository;

import org.soptorshi.domain.CommercialPackagingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialPackagingDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialPackagingDetailsRepository extends JpaRepository<CommercialPackagingDetails, Long>, JpaSpecificationExecutor<CommercialPackagingDetails> {

}
