package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.service.dto.LeaveApplicationDTO;

/**
 * Mapper for the entity LeaveApplication and its DTO LeaveApplicationDTO.
 */
@Mapper(componentModel = "spring", uses = {LeaveTypeMapper.class, EmployeeMapper.class})
public interface LeaveApplicationMapper extends EntityMapper<LeaveApplicationDTO, LeaveApplication> {

    @Mapping(source = "leaveTypes.id", target = "leaveTypesId")
    @Mapping(source = "leaveTypes.name", target = "leaveTypesName")
    @Mapping(source = "employees.id", target = "employeesId")
    @Mapping(source = "employees.fullName", target = "employeesFullName")
    @Mapping(source = "appliedById.id", target = "appliedByIdId")
    @Mapping(source = "appliedById.fullName", target = "appliedByIdFullName")
    @Mapping(source = "actionTakenById.id", target = "actionTakenByIdId")
    @Mapping(source = "actionTakenById.fullName", target = "actionTakenByIdFullName")
    LeaveApplicationDTO toDto(LeaveApplication leaveApplication);

    @Mapping(source = "leaveTypesId", target = "leaveTypes")
    @Mapping(source = "employeesId", target = "employees")
    @Mapping(source = "appliedByIdId", target = "appliedById")
    @Mapping(source = "actionTakenByIdId", target = "actionTakenById")
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
