package org.soptorshi.repository.extended;

import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.SupplyOrderDetails;
import org.soptorshi.repository.SupplyOrderDetailsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the SupplyOrderDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyOrderDetailsExtendedRepository extends SupplyOrderDetailsRepository {

    List<SupplyOrderDetails> getAllBySupplyOrder(SupplyOrder supplyOrder);
}
