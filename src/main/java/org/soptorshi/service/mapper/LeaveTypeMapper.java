package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.LeaveTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LeaveType and its DTO LeaveTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LeaveTypeMapper extends EntityMapper<LeaveTypeDTO, LeaveType> {



    default LeaveType fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeaveType leaveType = new LeaveType();
        leaveType.setId(id);
        return leaveType;
    }
}
