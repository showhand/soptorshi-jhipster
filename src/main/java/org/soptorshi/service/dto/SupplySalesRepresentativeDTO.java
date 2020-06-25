package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.SupplySalesRepresentativeStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the SupplySalesRepresentative entity.
 */
public class SupplySalesRepresentativeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String contact;

    private String email;

    private String additionalInformation;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    @NotNull
    private SupplySalesRepresentativeStatus status;


    private Long supplyZoneId;

    private String supplyZoneName;

    private Long supplyAreaId;

    private String supplyAreaName;

    private Long supplyZoneManagerId;

    private Long supplyAreaManagerId;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
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

    public SupplySalesRepresentativeStatus getStatus() {
        return status;
    }

    public void setStatus(SupplySalesRepresentativeStatus status) {
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

    public Long getSupplyZoneManagerId() {
        return supplyZoneManagerId;
    }

    public void setSupplyZoneManagerId(Long supplyZoneManagerId) {
        this.supplyZoneManagerId = supplyZoneManagerId;
    }

    public Long getSupplyAreaManagerId() {
        return supplyAreaManagerId;
    }

    public void setSupplyAreaManagerId(Long supplyAreaManagerId) {
        this.supplyAreaManagerId = supplyAreaManagerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplySalesRepresentativeDTO supplySalesRepresentativeDTO = (SupplySalesRepresentativeDTO) o;
        if (supplySalesRepresentativeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplySalesRepresentativeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplySalesRepresentativeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            ", additionalInformation='" + getAdditionalInformation() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", status='" + getStatus() + "'" +
            ", supplyZone=" + getSupplyZoneId() +
            ", supplyZone='" + getSupplyZoneName() + "'" +
            ", supplyArea=" + getSupplyAreaId() +
            ", supplyArea='" + getSupplyAreaName() + "'" +
            ", supplyZoneManager=" + getSupplyZoneManagerId() +
            ", supplyAreaManager=" + getSupplyAreaManagerId() +
            "}";
    }
}
