package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Quotation entity.
 */
public class QuotationDTO implements Serializable {

    private Long id;

    private String quotationNo;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long requisitionId;

    private String requisitionRequisitionNo;

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
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", requisition=" + getRequisitionId() +
            ", requisition='" + getRequisitionRequisitionNo() + "'" +
            "}";
    }
}
