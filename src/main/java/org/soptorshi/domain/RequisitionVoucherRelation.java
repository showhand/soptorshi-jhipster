package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A RequisitionVoucherRelation.
 */
@Entity
@Table(name = "requisition_voucher_relation")
@Document(indexName = "requisitionvoucherrelation")
public class RequisitionVoucherRelation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_no")
    private String voucherNo;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("requisitionVoucherRelations")
    private Voucher voucher;

    @ManyToOne
    @JsonIgnoreProperties("requisitionVoucherRelations")
    private Requisition requisition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public RequisitionVoucherRelation voucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
        return this;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public RequisitionVoucherRelation amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public RequisitionVoucherRelation modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public RequisitionVoucherRelation modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public RequisitionVoucherRelation voucher(Voucher voucher) {
        this.voucher = voucher;
        return this;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public RequisitionVoucherRelation requisition(Requisition requisition) {
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
        RequisitionVoucherRelation requisitionVoucherRelation = (RequisitionVoucherRelation) o;
        if (requisitionVoucherRelation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requisitionVoucherRelation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequisitionVoucherRelation{" +
            "id=" + getId() +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", amount=" + getAmount() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
