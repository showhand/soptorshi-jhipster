package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.RequisitionVoucherRelationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RequisitionVoucherRelation and its DTO RequisitionVoucherRelationDTO.
 */
@Mapper(componentModel = "spring", uses = {RequisitionMapper.class})
public interface RequisitionVoucherRelationMapper extends EntityMapper<RequisitionVoucherRelationDTO, RequisitionVoucherRelation> {

    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "requisition.requisitionNo", target = "requisitionRequisitionNo")
    RequisitionVoucherRelationDTO toDto(RequisitionVoucherRelation requisitionVoucherRelation);

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
