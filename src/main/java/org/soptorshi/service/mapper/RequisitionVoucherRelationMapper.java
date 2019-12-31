package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.RequisitionVoucherRelationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RequisitionVoucherRelation and its DTO RequisitionVoucherRelationDTO.
 */
@Mapper(componentModel = "spring", uses = {VoucherMapper.class, RequisitionMapper.class})
public interface RequisitionVoucherRelationMapper extends EntityMapper<RequisitionVoucherRelationDTO, RequisitionVoucherRelation> {

    @Mapping(source = "voucher.id", target = "voucherId")
    @Mapping(source = "voucher.name", target = "voucherName")
    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "requisition.requisitionNo", target = "requisitionRequisitionNo")
    RequisitionVoucherRelationDTO toDto(RequisitionVoucherRelation requisitionVoucherRelation);

    @Mapping(source = "voucherId", target = "voucher")
    @Mapping(source = "requisitionId", target = "requisition")
    RequisitionVoucherRelation toEntity(RequisitionVoucherRelationDTO requisitionVoucherRelationDTO);

    default RequisitionVoucherRelation fromId(Long id) {
        if (id == null) {
            return null;
        }
        RequisitionVoucherRelation requisitionVoucherRelation = new RequisitionVoucherRelation();
        requisitionVoucherRelation.setId(id);
        return requisitionVoucherRelation;
    }
}
