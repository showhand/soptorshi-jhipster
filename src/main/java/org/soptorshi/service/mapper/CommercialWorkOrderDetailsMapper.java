package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.CommercialWorkOrderDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommercialWorkOrderDetails and its DTO CommercialWorkOrderDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialWorkOrderMapper.class})
public interface CommercialWorkOrderDetailsMapper extends EntityMapper<CommercialWorkOrderDetailsDTO, CommercialWorkOrderDetails> {

    @Mapping(source = "commercialWorkOrder.id", target = "commercialWorkOrderId")
    @Mapping(source = "commercialWorkOrder.refNo", target = "commercialWorkOrderRefNo")
    CommercialWorkOrderDetailsDTO toDto(CommercialWorkOrderDetails commercialWorkOrderDetails);

    @Mapping(source = "commercialWorkOrderId", target = "commercialWorkOrder")
    CommercialWorkOrderDetails toEntity(CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO);

    default CommercialWorkOrderDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialWorkOrderDetails commercialWorkOrderDetails = new CommercialWorkOrderDetails();
        commercialWorkOrderDetails.setId(id);
        return commercialWorkOrderDetails;
    }
}
