package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.LeaveApplicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LeaveApplication and its DTO LeaveApplicationDTO.
 */
@Mapper(componentModel = "spring", uses = {LeaveTypeMapper.class})
public interface LeaveApplicationMapper extends EntityMapper<LeaveApplicationDTO, LeaveApplication> {


    @Mapping(source = "leaveTypes.id", target = "leaveTypesId")
    @Mapping(source = "leaveTypes.name", target = "leaveTypesName")
    LeaveApplicationDTO toDto(LeaveApplication leaveApplication);

    @Mapping(source = "leaveTypesId", target = "leaveTypes")
    LeaveApplication toEntity(LeaveApplicationDTO leaveApplicationDTO);

    default LeaveApplication fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId(id);
        return leaveApplication;
    }
}
