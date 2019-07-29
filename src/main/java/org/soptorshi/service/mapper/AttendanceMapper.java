package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.AttendanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Attendance and its DTO AttendanceDTO.
 */
@Mapper(componentModel = "spring", uses = {AttendanceExcelUploadMapper.class})
public interface AttendanceMapper extends EntityMapper<AttendanceDTO, Attendance> {

    @Mapping(source = "attendanceExcelUpload.id", target = "attendanceExcelUploadId")
    AttendanceDTO toDto(Attendance attendance);

    @Mapping(source = "attendanceExcelUploadId", target = "attendanceExcelUpload")
    Attendance toEntity(AttendanceDTO attendanceDTO);

    default Attendance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attendance attendance = new Attendance();
        attendance.setId(id);
        return attendance;
    }
}
