package org.soptorshi.repository;

import org.soptorshi.domain.CommercialWorkOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialWorkOrderDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialWorkOrderDetailsRepository extends JpaRepository<CommercialWorkOrderDetails, Long>, JpaSpecificationExecutor<CommercialWorkOrderDetails> {

}
