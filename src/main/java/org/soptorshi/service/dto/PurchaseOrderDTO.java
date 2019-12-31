package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import org.soptorshi.domain.enumeration.PurchaseOrderStatus;

/**
 * A DTO for the PurchaseOrder entity.
 */
public class PurchaseOrderDTO implements Serializable {

    private Long id;

    private String purchaseOrderNo;

    private String workOrderNo;

    private LocalDate issueDate;

    private String referredTo;

    private String subject;

    @Lob
    private String note;

    private BigDecimal laborOrOtherAmount;

    private Double discount;

    private PurchaseOrderStatus status;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long requisitionId;

    private String requisitionRequisitionNo;

    private Long quotationId;

    private String quotationQuotationNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getReferredTo() {
        return referredTo;
    }

    public void setReferredTo(String referredTo) {
        this.referredTo = referredTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getLaborOrOtherAmount() {
        return laborOrOtherAmount;
    }

    public void setLaborOrOtherAmount(BigDecimal laborOrOtherAmount) {
        this.laborOrOtherAmount = laborOrOtherAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
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

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public String getQuotationQuotationNo() {
        return quotationQuotationNo;
    }

    public void setQuotationQuotationNo(String quotationQuotationNo) {
        this.quotationQuotationNo = quotationQuotationNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PurchaseOrderDTO purchaseOrderDTO = (PurchaseOrderDTO) o;
        if (purchaseOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseOrderDTO{" +
            "id=" + getId() +
            ", purchaseOrderNo='" + getPurchaseOrderNo() + "'" +
            ", workOrderNo='" + getWorkOrderNo() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", referredTo='" + getReferredTo() + "'" +
            ", subject='" + getSubject() + "'" +
            ", note='" + getNote() + "'" +
            ", laborOrOtherAmount=" + getLaborOrOtherAmount() +
            ", discount=" + getDiscount() +
            ", status='" + getStatus() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", requisition=" + getRequisitionId() +
            ", requisition='" + getRequisitionRequisitionNo() + "'" +
            ", quotation=" + getQuotationId() +
            ", quotation='" + getQuotationQuotationNo() + "'" +
            "}";
    }
}
