package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.AttendanceExcelUploadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AttendanceExcelUpload and its DTO AttendanceExcelUploadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttendanceExcelUploadMapper extends EntityMapper<AttendanceExcelUploadDTO, AttendanceExcelUpload> {



    default AttendanceExcelUpload fromId(Long id) {
        if (id == null) {
            return null;
        }
        AttendanceExcelUpload attendanceExcelUpload = new AttendanceExcelUpload();
        attendanceExcelUpload.setId(id);
        return attendanceExcelUpload;
    }
}
