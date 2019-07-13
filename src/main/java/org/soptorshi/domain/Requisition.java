package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.RequisitionStatus;

/**
 * A Requisition.
 */
@Entity
@Table(name = "requisition")
@Document(indexName = "requisition")
public class Requisition implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requisition_no")
    private String requisitionNo;

    @Lob
    @Column(name = "reason")
    private String reason;

    @Column(name = "requisition_date")
    private LocalDate requisitionDate;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequisitionStatus status;

    @Lob
    @Column(name = "purchase_committee_remarks")
    private String purchaseCommitteeRemarks;

    @Column(name = "ref_to_purchase_committee")
    private Long refToPurchaseCommittee;

    @Lob
    @Column(name = "cfo_remarks")
    private String cfoRemarks;

    @Column(name = "ref_to_cfo")
    private Long refToCfo;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("requisitions")
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties("requisitions")
    private Office office;

    @ManyToOne
    @JsonIgnoreProperties("requisitions")
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties("requisitions")
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequisitionNo() {
        return requisitionNo;
    }

    public Requisition requisitionNo(String requisitionNo) {
        this.requisitionNo = requisitionNo;
        return this;
    }

    public void setRequisitionNo(String requisitionNo) {
        this.requisitionNo = requisitionNo;
    }

    public String getReason() {
        return reason;
    }

    public Requisition reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getRequisitionDate() {
        return requisitionDate;
    }

    public Requisition requisitionDate(LocalDate requisitionDate) {
        this.requisitionDate = requisitionDate;
        return this;
    }

    public void setRequisitionDate(LocalDate requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Requisition amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public RequisitionStatus getStatus() {
        return status;
    }

    public Requisition status(RequisitionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(RequisitionStatus status) {
        this.status = status;
    }

    public String getPurchaseCommitteeRemarks() {
        return purchaseCommitteeRemarks;
    }

    public Requisition purchaseCommitteeRemarks(String purchaseCommitteeRemarks) {
        this.purchaseCommitteeRemarks = purchaseCommitteeRemarks;
        return this;
    }

    public void setPurchaseCommitteeRemarks(String purchaseCommitteeRemarks) {
        this.purchaseCommitteeRemarks = purchaseCommitteeRemarks;
    }

    public Long getRefToPurchaseCommittee() {
        return refToPurchaseCommittee;
    }

    public Requisition refToPurchaseCommittee(Long refToPurchaseCommittee) {
        this.refToPurchaseCommittee = refToPurchaseCommittee;
        return this;
    }

    public void setRefToPurchaseCommittee(Long refToPurchaseCommittee) {
        this.refToPurchaseCommittee = refToPurchaseCommittee;
    }

    public String getCfoRemarks() {
        return cfoRemarks;
    }

    public Requisition cfoRemarks(String cfoRemarks) {
        this.cfoRemarks = cfoRemarks;
        return this;
    }

    public void setCfoRemarks(String cfoRemarks) {
        this.cfoRemarks = cfoRemarks;
    }

    public Long getRefToCfo() {
        return refToCfo;
    }

    public Requisition refToCfo(Long refToCfo) {
        this.refToCfo = refToCfo;
        return this;
    }

    public void setRefToCfo(Long refToCfo) {
        this.refToCfo = refToCfo;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Requisition modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public Requisition modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Requisition employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Office getOffice() {
        return office;
    }

    public Requisition office(Office office) {
        this.office = office;
        return this;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public Requisition productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Department getDepartment() {
        return department;
    }

    public Requisition department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        Requisition requisition = (Requisition) o;
        if (requisition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requisition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Requisition{" +
            "id=" + getId() +
            ", requisitionNo='" + getRequisitionNo() + "'" +
            ", reason='" + getReason() + "'" +
            ", requisitionDate='" + getRequisitionDate() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", purchaseCommitteeRemarks='" + getPurchaseCommitteeRemarks() + "'" +
            ", refToPurchaseCommittee=" + getRefToPurchaseCommittee() +
            ", cfoRemarks='" + getCfoRemarks() + "'" +
            ", refToCfo=" + getRefToCfo() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
