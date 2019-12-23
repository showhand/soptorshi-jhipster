package org.soptorshi.repository;

import org.soptorshi.domain.SupplyMoneyCollection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyMoneyCollection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyMoneyCollectionRepository extends JpaRepository<SupplyMoneyCollection, Long>, JpaSpecificationExecutor<SupplyMoneyCollection> {

}
