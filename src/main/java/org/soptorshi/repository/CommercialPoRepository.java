package org.soptorshi.repository;

import org.soptorshi.domain.CommercialPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommercialPo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialPoRepository extends JpaRepository<CommercialPo, Long>, JpaSpecificationExecutor<CommercialPo> {

}
