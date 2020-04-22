package org.soptorshi.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the OverTime entity.
 */
public class OverTimeDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate overTimeDate;

    @NotNull
    private Instant fromTime;

    @NotNull
    private Instant toTime;

    private String duration;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    private Long employeeId;

    private String employeeFullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOverTimeDate() {
        return overTimeDate;
    }

    public void setOverTimeDate(LocalDate overTimeDate) {
        this.overTimeDate = overTimeDate;
    }

    public Instant getFromTime() {
        return fromTime;
    }

    public void setFromTime(Instant fromTime) {
        this.fromTime = fromTime;
    }

    public Instant getToTime() {
        return toTime;
    }

    public void setToTime(Instant toTime) {
        this.toTime = toTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OverTimeDTO overTimeDTO = (OverTimeDTO) o;
        if (overTimeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), overTimeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OverTimeDTO{" +
            "id=" + getId() +
            ", overTimeDate='" + getOverTimeDate() + "'" +
            ", fromTime='" + getFromTime() + "'" +
            ", toTime='" + getToTime() + "'" +
            ", duration='" + getDuration() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeFullName() + "'" +
            "}";
    }
}
