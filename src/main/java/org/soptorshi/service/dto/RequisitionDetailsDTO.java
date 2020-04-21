package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;

/**
 * A DTO for the RequisitionDetails entity.
 */
public class RequisitionDetailsDTO implements Serializable {

    private Long id;

    private LocalDate requiredOn;

    private LocalDate estimatedDate;

    private UnitOfMeasurements uom;

    private Integer unit;

    private BigDecimal unitPrice;

    private BigDecimal quantity;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long productCategoryId;

    private String productCategoryName;

    private Long requisitionId;

    private String requisitionRequisitionNo;

    private Long productId;

    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRequiredOn() {
        return requiredOn;
    }

    public void setRequiredOn(LocalDate requiredOn) {
        this.requiredOn = requiredOn;
    }

    public LocalDate getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(LocalDate estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public UnitOfMeasurements getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasurements uom) {
        this.uom = uom;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Long getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(Long requisitionId) {
        this.requisitionId = requisitionId;
    }

    public String getRequisitionRequisitionNo() {
        return requisitionRequisitionNo;
    }

    public void setRequisitionRequisitionNo(String requisitionRequisitionNo) {
        this.requisitionRequisitionNo = requisitionRequisitionNo;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequisitionDetailsDTO requisitionDetailsDTO = (RequisitionDetailsDTO) o;
        if (requisitionDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requisitionDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequisitionDetailsDTO{" +
            "id=" + getId() +
            ", requiredOn='" + getRequiredOn() + "'" +
            ", estimatedDate='" + getEstimatedDate() + "'" +
            ", uom='" + getUom() + "'" +
            ", unit=" + getUnit() +
            ", unitPrice=" + getUnitPrice() +
            ", quantity=" + getQuantity() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", productCategory=" + getProductCategoryId() +
            ", productCategory='" + getProductCategoryName() + "'" +
            ", requisition=" + getRequisitionId() +
            ", requisition='" + getRequisitionRequisitionNo() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductName() + "'" +
            "}";
    }
}
