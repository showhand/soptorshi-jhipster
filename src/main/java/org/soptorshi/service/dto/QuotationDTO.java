package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import org.soptorshi.domain.enumeration.SelectionType;

/**
 * A DTO for the Quotation entity.
 */
public class QuotationDTO implements Serializable {

    private Long id;

    private String quotationNo;

    
    @Lob
    private byte[] attachment;

    private String attachmentContentType;
    private SelectionType selectionStatus;

    private BigDecimal totalAmount;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long requisitionId;

    private String requisitionRequisitionNo;

    private Long vendorId;

    private String vendorCompanyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public SelectionType getSelectionStatus() {
        return selectionStatus;
    }

    public void setSelectionStatus(SelectionType selectionStatus) {
        this.selectionStatus = selectionStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorCompanyName() {
        return vendorCompanyName;
    }

    public void setVendorCompanyName(String vendorCompanyName) {
        this.vendorCompanyName = vendorCompanyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuotationDTO quotationDTO = (QuotationDTO) o;
        if (quotationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuotationDTO{" +
            "id=" + getId() +
            ", quotationNo='" + getQuotationNo() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", selectionStatus='" + getSelectionStatus() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", requisition=" + getRequisitionId() +
            ", requisition='" + getRequisitionRequisitionNo() + "'" +
            ", vendor=" + getVendorId() +
            ", vendor='" + getVendorCompanyName() + "'" +
            "}";
    }
}
