package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A WorkOrder.
 */
@Entity
@Table(name = "work_order")
@Document(indexName = "workorder")
public class WorkOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_no")
    private String referenceNo;

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

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("workOrders")
    private Requisition requisition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public WorkOrder referenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
        return this;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public WorkOrder issueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getReferredTo() {
        return referredTo;
    }

    public WorkOrder referredTo(String referredTo) {
        this.referredTo = referredTo;
        return this;
    }

    public void setReferredTo(String referredTo) {
        this.referredTo = referredTo;
    }

    public String getSubject() {
        return subject;
    }

    public WorkOrder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNote() {
        return note;
    }

    public WorkOrder note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getLaborOrOtherAmount() {
        return laborOrOtherAmount;
    }

    public WorkOrder laborOrOtherAmount(BigDecimal laborOrOtherAmount) {
        this.laborOrOtherAmount = laborOrOtherAmount;
        return this;
    }

    public void setLaborOrOtherAmount(BigDecimal laborOrOtherAmount) {
        this.laborOrOtherAmount = laborOrOtherAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public WorkOrder discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public WorkOrder modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public WorkOrder modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public WorkOrder requisition(Requisition requisition) {
        this.requisition = requisition;
        return this;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
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
        WorkOrder workOrder = (WorkOrder) o;
        if (workOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
            "id=" + getId() +
            ", referenceNo='" + getReferenceNo() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", referredTo='" + getReferredTo() + "'" +
            ", subject='" + getSubject() + "'" +
            ", note='" + getNote() + "'" +
            ", laborOrOtherAmount=" + getLaborOrOtherAmount() +
            ", discount=" + getDiscount() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
