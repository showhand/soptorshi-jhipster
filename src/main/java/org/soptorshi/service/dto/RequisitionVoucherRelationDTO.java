package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the RequisitionVoucherRelation entity.
 */
public class RequisitionVoucherRelationDTO implements Serializable {

    private Long id;

    private String voucherNo;

    private BigDecimal amount;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long voucherId;

    private String voucherName;

    private Long requisitionId;

    private String requisitionRequisitionNo;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequisitionVoucherRelationDTO requisitionVoucherRelationDTO = (RequisitionVoucherRelationDTO) o;
        if (requisitionVoucherRelationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requisitionVoucherRelationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequisitionVoucherRelationDTO{" +
            "id=" + getId() +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", amount=" + getAmount() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", voucher=" + getVoucherId() +
            ", voucher='" + getVoucherName() + "'" +
            ", requisition=" + getRequisitionId() +
            ", requisition='" + getRequisitionRequisitionNo() + "'" +
            "}";
    }
}
