package org.soptorshi.repository.extended;

import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.repository.SupplyOrderRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the SupplyOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyOrderExtendedRepository extends SupplyOrderRepository {

   Optional<List<SupplyOrder>> getByDateOfOrderGreaterThanEqualAndDateOfOrderLessThanEqualAndStatus(LocalDate fromDate, LocalDate toDate, SupplyOrderStatus status);

   List<SupplyOrder> getByAreaWiseAccumulationRefNo(String areaWiseAccumulationRefNo);

   List<SupplyOrder> getAllByDateOfOrderIsAfterAndDateOfOrderBefore(LocalDate before, LocalDate after);
}
