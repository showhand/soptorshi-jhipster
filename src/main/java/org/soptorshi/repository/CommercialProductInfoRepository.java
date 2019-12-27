package org.soptorshi.repository;

import org.soptorshi.domain.CommercialProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialProductInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialProductInfoRepository extends JpaRepository<CommercialProductInfo, Long>, JpaSpecificationExecutor<CommercialProductInfo> {

}
