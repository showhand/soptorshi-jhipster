package org.soptorshi.repository;

import org.soptorshi.domain.ProductPrice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProductPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long>, JpaSpecificationExecutor<ProductPrice> {

}
