package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

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

import org.soptorshi.domain.enumeration.SelectionType;

/**
 * A Quotation.
 */
@Entity
@Table(name = "quotation")
@Document(indexName = "quotation")
public class Quotation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quotation_no")
    private String quotationNo;

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

    
    @Lob
    @Column(name = "attachment", nullable = false)
    private byte[] attachment;

    @Column(name = "attachment_content_type", nullable = false)
    private String attachmentContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "selection_status")
    private SelectionType selectionStatus;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("quotations")
    private Requisition requisition;

    @ManyToOne
    @JsonIgnoreProperties("quotations")
    private Vendor vendor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuotationNo() {
        return quotationNo;
    }

    public Quotation quotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
        return this;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Quotation currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PayType getPayType() {
        return payType;
    }

    public Quotation payType(PayType payType) {
        this.payType = payType;
        return this;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public Quotation creditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
        return this;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public VatStatus getVatStatus() {
        return vatStatus;
    }

    public Quotation vatStatus(VatStatus vatStatus) {
        this.vatStatus = vatStatus;
        return this;
    }

    public void setVatStatus(VatStatus vatStatus) {
        this.vatStatus = vatStatus;
    }

    public AITStatus getAitStatus() {
        return aitStatus;
    }

    public Quotation aitStatus(AITStatus aitStatus) {
        this.aitStatus = aitStatus;
        return this;
    }

    public void setAitStatus(AITStatus aitStatus) {
        this.aitStatus = aitStatus;
    }

    public WarrantyStatus getWarrantyStatus() {
        return warrantyStatus;
    }

    public Quotation warrantyStatus(WarrantyStatus warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
        return this;
    }

    public void setWarrantyStatus(WarrantyStatus warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
    }

    public String getLoadingPort() {
        return loadingPort;
    }

    public Quotation loadingPort(String loadingPort) {
        this.loadingPort = loadingPort;
        return this;
    }

    public void setLoadingPort(String loadingPort) {
        this.loadingPort = loadingPort;
    }

    public String getRemarks() {
        return remarks;
    }

    public Quotation remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public Quotation attachment(byte[] attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public Quotation attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public SelectionType getSelectionStatus() {
        return selectionStatus;
    }

    public Quotation selectionStatus(SelectionType selectionStatus) {
        this.selectionStatus = selectionStatus;
        return this;
    }

    public void setSelectionStatus(SelectionType selectionStatus) {
        this.selectionStatus = selectionStatus;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Quotation modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public Quotation modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public Quotation requisition(Requisition requisition) {
        this.requisition = requisition;
        return this;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public Quotation vendor(Vendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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
        Quotation quotation = (Quotation) o;
        if (quotation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Quotation{" +
            "id=" + getId() +
            ", quotationNo='" + getQuotationNo() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", payType='" + getPayType() + "'" +
            ", creditLimit=" + getCreditLimit() +
            ", vatStatus='" + getVatStatus() + "'" +
            ", aitStatus='" + getAitStatus() + "'" +
            ", warrantyStatus='" + getWarrantyStatus() + "'" +
            ", loadingPort='" + getLoadingPort() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            ", selectionStatus='" + getSelectionStatus() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
