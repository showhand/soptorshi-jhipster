package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.PurchaseOrderMessagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchaseOrderMessages and its DTO PurchaseOrderMessagesDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, PurchaseOrderMapper.class})
public interface PurchaseOrderMessagesMapper extends EntityMapper<PurchaseOrderMessagesDTO, PurchaseOrderMessages> {

    @Mapping(source = "commenter.id", target = "commenterId")
    @Mapping(source = "commenter.fullName", target = "commenterFullName")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    PurchaseOrderMessagesDTO toDto(PurchaseOrderMessages purchaseOrderMessages);

    @Mapping(source = "commenterId", target = "commenter")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    PurchaseOrderMessages toEntity(PurchaseOrderMessagesDTO purchaseOrderMessagesDTO);

    default PurchaseOrderMessages fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrderMessages purchaseOrderMessages = new PurchaseOrderMessages();
        purchaseOrderMessages.setId(id);
        return purchaseOrderMessages;
    }
}
