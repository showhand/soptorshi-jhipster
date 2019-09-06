package org.soptorshi.repository;

import org.soptorshi.domain.ConversionFactor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConversionFactor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConversionFactorRepository extends JpaRepository<ConversionFactor, Long>, JpaSpecificationExecutor<ConversionFactor> {

}
