package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Attendance.
 */
@Entity
@Table(name = "attendance")
@Document(indexName = "attendance")
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @Column(name = "in_time")
    private Instant inTime;

    @Column(name = "out_time")
    private Instant outTime;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("attendances")
    private AttendanceExcelUpload attendanceExcelUpload;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Attendance employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public Attendance attendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
        return this;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Instant getInTime() {
        return inTime;
    }

    public Attendance inTime(Instant inTime) {
        this.inTime = inTime;
        return this;
    }

    public void setInTime(Instant inTime) {
        this.inTime = inTime;
    }

    public Instant getOutTime() {
        return outTime;
    }

    public Attendance outTime(Instant outTime) {
        this.outTime = outTime;
        return this;
    }

    public void setOutTime(Instant outTime) {
        this.outTime = outTime;
    }

    public AttendanceExcelUpload getAttendanceExcelUpload() {
        return attendanceExcelUpload;
    }

    public Attendance attendanceExcelUpload(AttendanceExcelUpload attendanceExcelUpload) {
        this.attendanceExcelUpload = attendanceExcelUpload;
        return this;
    }

    public void setAttendanceExcelUpload(AttendanceExcelUpload attendanceExcelUpload) {
        this.attendanceExcelUpload = attendanceExcelUpload;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance attendance = (Attendance) o;
        if (attendance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Attendance{" +
            "id=" + getId() +
            ", employeeId='" + getEmployeeId() + "'" +
            ", attendanceDate='" + getAttendanceDate() + "'" +
            ", inTime='" + getInTime() + "'" +
            ", outTime='" + getOutTime() + "'" +
            "}";
    }
}
