package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.soptorshi.domain.enumeration.PurchaseOrderStatus;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "purchase_order")
@Document(indexName = "purchaseorder")
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_order_no")
    private String purchaseOrderNo;

    @Column(name = "work_order_no")
    private String workOrderNo;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "referred_to")
    private String referredTo;

    @Column(name = "subject")
    private String subject;

    @Lob
    @Column(name = "note")
    private String note;

    @Column(name = "labor_or_other_amount", precision = 10, scale = 2)
    private BigDecimal laborOrOtherAmount;

    @Column(name = "discount")
    private Double discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PurchaseOrderStatus status;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @OneToMany(mappedBy = "purchaseOrder")
    private Set<PurchaseOrderMessages> comments = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("purchaseOrders")
    private Requisition requisition;

    @ManyToOne
    @JsonIgnoreProperties("purchaseOrders")
    private Quotation quotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public PurchaseOrder purchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
        return this;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public PurchaseOrder workOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
        return this;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public PurchaseOrder issueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getReferredTo() {
        return referredTo;
    }

    public PurchaseOrder referredTo(String referredTo) {
        this.referredTo = referredTo;
        return this;
    }

    public void setReferredTo(String referredTo) {
        this.referredTo = referredTo;
    }

    public String getSubject() {
        return subject;
    }

    public PurchaseOrder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNote() {
        return note;
    }

    public PurchaseOrder note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getLaborOrOtherAmount() {
        return laborOrOtherAmount;
    }

    public PurchaseOrder laborOrOtherAmount(BigDecimal laborOrOtherAmount) {
        this.laborOrOtherAmount = laborOrOtherAmount;
        return this;
    }

    public void setLaborOrOtherAmount(BigDecimal laborOrOtherAmount) {
        this.laborOrOtherAmount = laborOrOtherAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public PurchaseOrder discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public PurchaseOrder status(PurchaseOrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public PurchaseOrder modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public PurchaseOrder modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Set<PurchaseOrderMessages> getComments() {
        return comments;
    }

    public PurchaseOrder comments(Set<PurchaseOrderMessages> purchaseOrderMessages) {
        this.comments = purchaseOrderMessages;
        return this;
    }

    public PurchaseOrder addComments(PurchaseOrderMessages purchaseOrderMessages) {
        this.comments.add(purchaseOrderMessages);
        purchaseOrderMessages.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrder removeComments(PurchaseOrderMessages purchaseOrderMessages) {
        this.comments.remove(purchaseOrderMessages);
        purchaseOrderMessages.setPurchaseOrder(null);
        return this;
    }

    public void setComments(Set<PurchaseOrderMessages> purchaseOrderMessages) {
        this.comments = purchaseOrderMessages;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public PurchaseOrder requisition(Requisition requisition) {
        this.requisition = requisition;
        return this;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public PurchaseOrder quotation(Quotation quotation) {
        this.quotation = quotation;
        return this;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
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
        PurchaseOrder purchaseOrder = (PurchaseOrder) o;
        if (purchaseOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchaseOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
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
            "}";
    }
}
