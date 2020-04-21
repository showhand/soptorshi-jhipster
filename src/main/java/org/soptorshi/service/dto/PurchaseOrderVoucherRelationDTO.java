package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the PurchaseOrderVoucherRelation entity.
 */
public class PurchaseOrderVoucherRelationDTO implements Serializable {

    private Long id;

    private String voucherNo;

    private BigDecimal amount;

    private String createBy;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long voucherId;

    private String voucherName;

    private Long purchaseOrderId;

    private String purchaseOrderPurchaseOrderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseOrderPurchaseOrderNo() {
        return purchaseOrderPurchaseOrderNo;
    }

    public void setPurchaseOrderPurchaseOrderNo(String purchaseOrderPurchaseOrderNo) {
        this.purchaseOrderPurchaseOrderNo = purchaseOrderPurchaseOrderNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PurchaseOrderVoucherRelationDTO purchaseOrderVoucherRelationDTO = (PurchaseOrderVoucherRelationDTO) o;
        if (purchaseOrderVoucherRelationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseOrderVoucherRelationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseOrderVoucherRelationDTO{" +
            "id=" + getId() +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", amount=" + getAmount() +
            ", createBy='" + getCreateBy() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", voucher=" + getVoucherId() +
            ", voucher='" + getVoucherName() + "'" +
            ", purchaseOrder=" + getPurchaseOrderId() +
            ", purchaseOrder='" + getPurchaseOrderPurchaseOrderNo() + "'" +
            "}";
    }
}
