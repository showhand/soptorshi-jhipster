package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import org.soptorshi.domain.enumeration.Currency;
import org.soptorshi.domain.enumeration.PayType;
import org.soptorshi.domain.enumeration.VatStatus;
import org.soptorshi.domain.enumeration.AITStatus;
import org.soptorshi.domain.enumeration.WarrantyStatus;

/**
 * A DTO for the QuotationDetails entity.
 */
public class QuotationDetailsDTO implements Serializable {

    private Long id;

    private Currency currency;

    private BigDecimal rate;

    private Integer quantity;

    private PayType payType;

    private BigDecimal creditLimit;

    private VatStatus vatStatus;

    private AITStatus aitStatus;

    private WarrantyStatus warrantyStatus;

    private String loadingPort;

    @Lob
    private String remarks;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long quotationId;

    private String quotationQuotationNo;

    private Long requisitionDetailsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public VatStatus getVatStatus() {
        return vatStatus;
    }

    public void setVatStatus(VatStatus vatStatus) {
        this.vatStatus = vatStatus;
    }

    public AITStatus getAitStatus() {
        return aitStatus;
    }

    public void setAitStatus(AITStatus aitStatus) {
        this.aitStatus = aitStatus;
    }

    public WarrantyStatus getWarrantyStatus() {
        return warrantyStatus;
    }

    public void setWarrantyStatus(WarrantyStatus warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
    }

    public String getLoadingPort() {
        return loadingPort;
    }

    public void setLoadingPort(String loadingPort) {
        this.loadingPort = loadingPort;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Long getRequisitionDetailsId() {
        return requisitionDetailsId;
    }

    public void setRequisitionDetailsId(Long requisitionDetailsId) {
        this.requisitionDetailsId = requisitionDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuotationDetailsDTO quotationDetailsDTO = (QuotationDetailsDTO) o;
        if (quotationDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotationDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuotationDetailsDTO{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", rate=" + getRate() +
            ", quantity=" + getQuantity() +
            ", payType='" + getPayType() + "'" +
            ", creditLimit=" + getCreditLimit() +
            ", vatStatus='" + getVatStatus() + "'" +
            ", aitStatus='" + getAitStatus() + "'" +
            ", warrantyStatus='" + getWarrantyStatus() + "'" +
            ", loadingPort='" + getLoadingPort() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", quotation=" + getQuotationId() +
            ", quotation='" + getQuotationQuotationNo() + "'" +
            ", requisitionDetails=" + getRequisitionDetailsId() +
            "}";
    }
}
