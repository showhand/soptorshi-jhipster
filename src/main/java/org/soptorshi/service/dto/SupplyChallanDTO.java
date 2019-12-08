package org.soptorshi.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SupplyChallan entity.
 */
public class SupplyChallanDTO implements Serializable {

    private Long id;

    @NotNull
    private String challanNo;

    private LocalDate dateOfChallan;

    private String remarks;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    private Long supplyOrderId;

    private String supplyOrderOrderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public LocalDate getDateOfChallan() {
        return dateOfChallan;
    }

    public void setDateOfChallan(LocalDate dateOfChallan) {
        this.dateOfChallan = dateOfChallan;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Long getSupplyOrderId() {
        return supplyOrderId;
    }

    public void setSupplyOrderId(Long supplyOrderId) {
        this.supplyOrderId = supplyOrderId;
    }

    public String getSupplyOrderOrderNo() {
        return supplyOrderOrderNo;
    }

    public void setSupplyOrderOrderNo(String supplyOrderOrderNo) {
        this.supplyOrderOrderNo = supplyOrderOrderNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplyChallanDTO supplyChallanDTO = (SupplyChallanDTO) o;
        if (supplyChallanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyChallanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyChallanDTO{" +
            "id=" + getId() +
            ", challanNo='" + getChallanNo() + "'" +
            ", dateOfChallan='" + getDateOfChallan() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", supplyOrder=" + getSupplyOrderId() +
            ", supplyOrder='" + getSupplyOrderOrderNo() + "'" +
            "}";
    }
}
