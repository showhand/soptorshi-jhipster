package org.soptorshi.repository;

import org.soptorshi.domain.CommercialPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialPaymentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialPaymentInfoRepository extends JpaRepository<CommercialPaymentInfo, Long>, JpaSpecificationExecutor<CommercialPaymentInfo> {

}
