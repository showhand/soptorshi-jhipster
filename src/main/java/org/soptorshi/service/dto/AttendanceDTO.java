package org.soptorshi.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Attendance entity.
 */
public class AttendanceDTO implements Serializable {

    private Long id;

    private String employeeId;

    private LocalDate attendanceDate;

    private Instant inTime;

    private Instant outTime;


    private Long attendanceExcelUploadId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Instant getInTime() {
        return inTime;
    }

    public void setInTime(Instant inTime) {
        this.inTime = inTime;
    }

    public Instant getOutTime() {
        return outTime;
    }

    public void setOutTime(Instant outTime) {
        this.outTime = outTime;
    }

    public Long getAttendanceExcelUploadId() {
        return attendanceExcelUploadId;
    }

    public void setAttendanceExcelUploadId(Long attendanceExcelUploadId) {
        this.attendanceExcelUploadId = attendanceExcelUploadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttendanceDTO attendanceDTO = (AttendanceDTO) o;
        if (attendanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttendanceDTO{" +
            "id=" + getId() +
            ", employeeId='" + getEmployeeId() + "'" +
            ", attendanceDate='" + getAttendanceDate() + "'" +
            ", inTime='" + getInTime() + "'" +
            ", outTime='" + getOutTime() + "'" +
            ", attendanceExcelUpload=" + getAttendanceExcelUploadId() +
            "}";
    }
}
