package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.WorkOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WorkOrder and its DTO WorkOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {RequisitionMapper.class})
public interface WorkOrderMapper extends EntityMapper<WorkOrderDTO, WorkOrder> {

    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "requisition.requisitionNo", target = "requisitionRequisitionNo")
    WorkOrderDTO toDto(WorkOrder workOrder);

    @Mapping(source = "requisitionId", target = "requisition")
    WorkOrder toEntity(WorkOrderDTO workOrderDTO);

    default WorkOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(id);
        return workOrder;
    }
}
