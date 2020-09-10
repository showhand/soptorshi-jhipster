package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @NotNull
    @Column(name = "in_time", nullable = false)
    private Instant inTime;

    @Column(name = "out_time")
    private Instant outTime;

    @Column(name = "duration")
    private String duration;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("attendances")
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties("attendances")
    private AttendanceExcelUpload attendanceExcelUpload;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDuration() {
        return duration;
    }

    public Attendance duration(String duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRemarks() {
        return remarks;
    }

    public Attendance remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Attendance createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Attendance createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Attendance updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Attendance updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Attendance employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
            ", attendanceDate='" + getAttendanceDate() + "'" +
            ", inTime='" + getInTime() + "'" +
            ", outTime='" + getOutTime() + "'" +
            ", duration='" + getDuration() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
