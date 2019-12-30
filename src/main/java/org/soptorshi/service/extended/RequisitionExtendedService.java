package org.soptorshi.service.extended;

import org.soptorshi.repository.RequisitionRepository;
import org.soptorshi.repository.search.RequisitionSearchRepository;
import org.soptorshi.service.RequisitionService;
import org.soptorshi.service.mapper.RequisitionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequisitionExtendedService extends RequisitionService {
    public RequisitionExtendedService(RequisitionRepository requisitionRepository, RequisitionMapper requisitionMapper, RequisitionSearchRepository requisitionSearchRepository) {
        super(requisitionRepository, requisitionMapper, requisitionSearchRepository);
    }

}
