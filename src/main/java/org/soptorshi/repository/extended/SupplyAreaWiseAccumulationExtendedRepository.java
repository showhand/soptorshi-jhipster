package org.soptorshi.repository.extended;

import org.soptorshi.domain.SupplyAreaWiseAccumulation;
import org.soptorshi.repository.SupplyAreaWiseAccumulationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the SupplyAreaWiseAccumulation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyAreaWiseAccumulationExtendedRepository extends SupplyAreaWiseAccumulationRepository {

    List<SupplyAreaWiseAccumulation> getAllByZoneWiseAccumulationRefNo(String zoneWiseAccumulationRefNo);
}
