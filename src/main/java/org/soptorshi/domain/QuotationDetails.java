package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.Currency;

import org.soptorshi.domain.enumeration.PayType;

import org.soptorshi.domain.enumeration.VatStatus;

import org.soptorshi.domain.enumeration.AITStatus;

import org.soptorshi.domain.enumeration.WarrantyStatus;

/**
 * A QuotationDetails.
 */
@Entity
@Table(name = "quotation_details")
@Document(indexName = "quotationdetails")
public class QuotationDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_type")
    private PayType payType;

    @Column(name = "credit_limit", precision = 10, scale = 2)
    private BigDecimal creditLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "vat_status")
    private VatStatus vatStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "ait_status")
    private AITStatus aitStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "warranty_status")
    private WarrantyStatus warrantyStatus;

    @Column(name = "loading_port")
    private String loadingPort;

    @Lob
    @Column(name = "remarks")
    private String remarks;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("quotationDetails")
    private Quotation quotation;

    @ManyToOne
    @JsonIgnoreProperties("quotationDetails")
    private Vendor vendor;

    @ManyToOne
    @JsonIgnoreProperties("quotationDetails")
    private RequisitionDetails requisitionDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public QuotationDetails currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PayType getPayType() {
        return payType;
    }

    public QuotationDetails payType(PayType payType) {
        this.payType = payType;
        return this;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public QuotationDetails creditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public VatStatus getVatStatus() {
        return vatStatus;
    }

    public QuotationDetails vatStatus(VatStatus vatStatus) {
        this.vatStatus = vatStatus;
        return this;
    }

    public void setVatStatus(VatStatus vatStatus) {
        this.vatStatus = vatStatus;
    }

    public AITStatus getAitStatus() {
        return aitStatus;
    }

    public QuotationDetails aitStatus(AITStatus aitStatus) {
        this.aitStatus = aitStatus;
        return this;
    }

    public void setAitStatus(AITStatus aitStatus) {
        this.aitStatus = aitStatus;
    }

    public WarrantyStatus getWarrantyStatus() {
        return warrantyStatus;
    }

    public QuotationDetails warrantyStatus(WarrantyStatus warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
        return this;
    }

    public void setWarrantyStatus(WarrantyStatus warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
    }

    public String getLoadingPort() {
        return loadingPort;
    }

    public QuotationDetails loadingPort(String loadingPort) {
        this.loadingPort = loadingPort;
        return this;
    }

    public void setLoadingPort(String loadingPort) {
        this.loadingPort = loadingPort;
    }

    public String getRemarks() {
        return remarks;
    }

    public QuotationDetails remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public QuotationDetails modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public QuotationDetails modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public QuotationDetails quotation(Quotation quotation) {
        this.quotation = quotation;
        return this;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public QuotationDetails vendor(Vendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public RequisitionDetails getRequisitionDetails() {
        return requisitionDetails;
    }

    public QuotationDetails requisitionDetails(RequisitionDetails requisitionDetails) {
        this.requisitionDetails = requisitionDetails;
        return this;
    }

    public void setRequisitionDetails(RequisitionDetails requisitionDetails) {
        this.requisitionDetails = requisitionDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuotationDetails quotationDetails = (QuotationDetails) o;
        if (quotationDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotationDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuotationDetails{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", payType='" + getPayType() + "'" +
            ", creditLimit=" + getCreditLimit() +
            ", vatStatus='" + getVatStatus() + "'" +
            ", aitStatus='" + getAitStatus() + "'" +
            ", warrantyStatus='" + getWarrantyStatus() + "'" +
            ", loadingPort='" + getLoadingPort() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
