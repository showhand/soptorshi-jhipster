package org.soptorshi.service.extended;

import org.soptorshi.domain.enumeration.ApplicationType;
import org.soptorshi.repository.RequisitionVoucherRelationRepository;
import org.soptorshi.repository.extended.RequisitionVoucherRelationExtendedRepository;
import org.soptorshi.repository.search.RequisitionVoucherRelationSearchRepository;
import org.soptorshi.service.RequisitionVoucherRelationService;
import org.soptorshi.service.dto.*;
import org.soptorshi.service.mapper.RequisitionVoucherRelationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequisitionVoucherRelationExtendedService extends RequisitionVoucherRelationService {
    RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository;

    public RequisitionVoucherRelationExtendedService(RequisitionVoucherRelationRepository requisitionVoucherRelationRepository, RequisitionVoucherRelationMapper requisitionVoucherRelationMapper, RequisitionVoucherRelationSearchRepository requisitionVoucherRelationSearchRepository, RequisitionVoucherRelationExtendedRepository requisitionVoucherRelationExtendedRepository) {
        super(requisitionVoucherRelationRepository, requisitionVoucherRelationMapper, requisitionVoucherRelationSearchRepository);
        this.requisitionVoucherRelationExtendedRepository = requisitionVoucherRelationExtendedRepository;
    }

    public void storeRequisitionVoucherRelation(String voucherNo, ApplicationType applicationType, Long applicationId, Long id,  String voucherName) {
        RequisitionVoucherRelationDTO requisitionVoucherRelationDTO = new RequisitionVoucherRelationDTO();
        requisitionVoucherRelationDTO.setRequisitionId(applicationId);
        //requisitionVoucherRelationDTO.setVoucherId(id);
        requisitionVoucherRelationDTO.setVoucherName(voucherName);
        requisitionVoucherRelationDTO.setVoucherNo(voucherNo);
        super.save(requisitionVoucherRelationDTO);
    }
}
