package org.soptorshi.repository.extended;

import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.RequisitionDetails;
import org.soptorshi.repository.RequisitionDetailsRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequisitionDetailsExtendedRepository extends RequisitionDetailsRepository {
    List<RequisitionDetails> getByRequisition(Requisition requisition);
}
