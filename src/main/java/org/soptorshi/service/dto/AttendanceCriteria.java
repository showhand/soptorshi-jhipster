package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Attendance entity. This class is used in AttendanceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /attendances?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AttendanceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter attendanceDate;

    private InstantFilter inTime;

    private InstantFilter outTime;

    private StringFilter duration;

    private StringFilter remarks;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter employeeId;

    private LongFilter attendanceExcelUploadId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDateFilter attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public InstantFilter getInTime() {
        return inTime;
    }

    public void setInTime(InstantFilter inTime) {
        this.inTime = inTime;
    }

    public InstantFilter getOutTime() {
        return outTime;
    }

    public void setOutTime(InstantFilter outTime) {
        this.outTime = outTime;
    }

    public StringFilter getDuration() {
        return duration;
    }

    public void setDuration(StringFilter duration) {
        this.duration = duration;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getAttendanceExcelUploadId() {
        return attendanceExcelUploadId;
    }

    public void setAttendanceExcelUploadId(LongFilter attendanceExcelUploadId) {
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
        final AttendanceCriteria that = (AttendanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(attendanceDate, that.attendanceDate) &&
            Objects.equals(inTime, that.inTime) &&
            Objects.equals(outTime, that.outTime) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(attendanceExcelUploadId, that.attendanceExcelUploadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        attendanceDate,
        inTime,
        outTime,
        duration,
        remarks,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        employeeId,
        attendanceExcelUploadId
        );
    }

    @Override
    public String toString() {
        return "AttendanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (attendanceDate != null ? "attendanceDate=" + attendanceDate + ", " : "") +
                (inTime != null ? "inTime=" + inTime + ", " : "") +
                (outTime != null ? "outTime=" + outTime + ", " : "") +
                (duration != null ? "duration=" + duration + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (attendanceExcelUploadId != null ? "attendanceExcelUploadId=" + attendanceExcelUploadId + ", " : "") +
            "}";
    }

}
