package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.CommercialStatus;

/**
 * A DTO for the CommercialPoStatus entity.
 */
public class CommercialPoStatusDTO implements Serializable {

    private Long id;

    @NotNull
    private CommercialStatus status;

    private String createdBy;

    private LocalDate createdOn;

    private String updatedBy;

    private LocalDate updatedOn;


    private Long commercialPurchaseOrderId;

    private String commercialPurchaseOrderPurchaseOrderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommercialStatus getStatus() {
        return status;
    }

    public void setStatus(CommercialStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getCommercialPurchaseOrderId() {
        return commercialPurchaseOrderId;
    }

    public void setCommercialPurchaseOrderId(Long commercialPurchaseOrderId) {
        this.commercialPurchaseOrderId = commercialPurchaseOrderId;
    }

    public String getCommercialPurchaseOrderPurchaseOrderNo() {
        return commercialPurchaseOrderPurchaseOrderNo;
    }

    public void setCommercialPurchaseOrderPurchaseOrderNo(String commercialPurchaseOrderPurchaseOrderNo) {
        this.commercialPurchaseOrderPurchaseOrderNo = commercialPurchaseOrderPurchaseOrderNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialPoStatusDTO commercialPoStatusDTO = (CommercialPoStatusDTO) o;
        if (commercialPoStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPoStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPoStatusDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", commercialPurchaseOrder=" + getCommercialPurchaseOrderId() +
            ", commercialPurchaseOrder='" + getCommercialPurchaseOrderPurchaseOrderNo() + "'" +
            "}";
    }
}
