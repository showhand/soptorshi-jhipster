package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the SupplyAreaManager entity.
 */
public class SupplyAreaManagerDTO implements Serializable {

    private Long id;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private LocalDate endDate;

    @NotNull
    private SupplyAreaManagerStatus status;


    private Long supplyZoneId;

    private String supplyZoneName;

    private Long supplyAreaId;

    private String supplyAreaName;

    private Long employeeId;

    private String employeeFullName;

    private Long supplyZoneManagerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SupplyAreaManagerStatus getStatus() {
        return status;
    }

    public void setStatus(SupplyAreaManagerStatus status) {
        this.status = status;
    }

    public Long getSupplyZoneId() {
        return supplyZoneId;
    }

    public void setSupplyZoneId(Long supplyZoneId) {
        this.supplyZoneId = supplyZoneId;
    }

    public String getSupplyZoneName() {
        return supplyZoneName;
    }

    public void setSupplyZoneName(String supplyZoneName) {
        this.supplyZoneName = supplyZoneName;
    }

    public Long getSupplyAreaId() {
        return supplyAreaId;
    }

    public void setSupplyAreaId(Long supplyAreaId) {
        this.supplyAreaId = supplyAreaId;
    }

    public String getSupplyAreaName() {
        return supplyAreaName;
    }

    public void setSupplyAreaName(String supplyAreaName) {
        this.supplyAreaName = supplyAreaName;
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

    public Long getSupplyZoneManagerId() {
        return supplyZoneManagerId;
    }

    public void setSupplyZoneManagerId(Long supplyZoneManagerId) {
        this.supplyZoneManagerId = supplyZoneManagerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplyAreaManagerDTO supplyAreaManagerDTO = (SupplyAreaManagerDTO) o;
        if (supplyAreaManagerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyAreaManagerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyAreaManagerDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", supplyZone=" + getSupplyZoneId() +
            ", supplyZone='" + getSupplyZoneName() + "'" +
            ", supplyArea=" + getSupplyAreaId() +
            ", supplyArea='" + getSupplyAreaName() + "'" +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeFullName() + "'" +
            ", supplyZoneManager=" + getSupplyZoneManagerId() +
            "}";
    }
}
