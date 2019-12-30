package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.RequisitionMessagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RequisitionMessages and its DTO RequisitionMessagesDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, RequisitionMapper.class})
public interface RequisitionMessagesMapper extends EntityMapper<RequisitionMessagesDTO, RequisitionMessages> {

    @Mapping(source = "commenter.id", target = "commenterId")
    @Mapping(source = "commenter.fullName", target = "commenterFullName")
    @Mapping(source = "requisition.id", target = "requisitionId")
    RequisitionMessagesDTO toDto(RequisitionMessages requisitionMessages);

    @Mapping(source = "commenterId", target = "commenter")
    @Mapping(source = "requisitionId", target = "requisition")
    RequisitionMessages toEntity(RequisitionMessagesDTO requisitionMessagesDTO);

    default RequisitionMessages fromId(Long id) {
        if (id == null) {
            return null;
        }
        RequisitionMessages requisitionMessages = new RequisitionMessages();
        requisitionMessages.setId(id);
        return requisitionMessages;
    }
}
