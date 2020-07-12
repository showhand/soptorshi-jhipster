package org.soptorshi.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the SupplyArea entity.
 */
public class SupplyAreaDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String code;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    private Long supplyZoneId;

    private String supplyZoneName;

    private Long supplyZoneManagerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

        SupplyAreaDTO supplyAreaDTO = (SupplyAreaDTO) o;
        if (supplyAreaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyAreaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyAreaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", supplyZone=" + getSupplyZoneId() +
            ", supplyZone='" + getSupplyZoneName() + "'" +
            ", supplyZoneManager=" + getSupplyZoneManagerId() +
            "}";
    }
}
