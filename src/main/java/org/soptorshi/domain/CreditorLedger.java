package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.BalanceType;

import org.soptorshi.domain.enumeration.BillClosingFlag;

/**
 * A CreditorLedger.
 */
@Entity
@Table(name = "creditor_ledger")
@Document(indexName = "creditorledger")
public class CreditorLedger implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_no")
    private Integer serialNo;

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "bill_date")
    private LocalDate billDate;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "paid_amount", precision = 10, scale = 2)
    private BigDecimal paidAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "balance_type")
    private BalanceType balanceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "bill_closing_flag")
    private BillClosingFlag billClosingFlag;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "vat_no")
    private String vatNo;

    @Column(name = "cont_code")
    private String contCode;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("creditorLedgers")
    private Vendor vendor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public CreditorLedger serialNo(Integer serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public CreditorLedger billNo(String billNo) {
        this.billNo = billNo;
        return this;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public CreditorLedger billDate(LocalDate billDate) {
        this.billDate = billDate;
        return this;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CreditorLedger amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public CreditorLedger paidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
        return this;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BalanceType getBalanceType() {
        return balanceType;
    }

    public CreditorLedger balanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
        return this;
    }

    public void setBalanceType(BalanceType balanceType) {
        this.balanceType = balanceType;
    }

    public BillClosingFlag getBillClosingFlag() {
        return billClosingFlag;
    }

    public CreditorLedger billClosingFlag(BillClosingFlag billClosingFlag) {
        this.billClosingFlag = billClosingFlag;
        return this;
    }

    public void setBillClosingFlag(BillClosingFlag billClosingFlag) {
        this.billClosingFlag = billClosingFlag;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public CreditorLedger dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getVatNo() {
        return vatNo;
    }

    public CreditorLedger vatNo(String vatNo) {
        this.vatNo = vatNo;
        return this;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getContCode() {
        return contCode;
    }

    public CreditorLedger contCode(String contCode) {
        this.contCode = contCode;
        return this;
    }

    public void setContCode(String contCode) {
        this.contCode = contCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public CreditorLedger orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public CreditorLedger modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public CreditorLedger modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public CreditorLedger vendor(Vendor vendor) {
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
        CreditorLedger creditorLedger = (CreditorLedger) o;
        if (creditorLedger.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditorLedger.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditorLedger{" +
            "id=" + getId() +
            ", serialNo=" + getSerialNo() +
            ", billNo='" + getBillNo() + "'" +
            ", billDate='" + getBillDate() + "'" +
            ", amount=" + getAmount() +
            ", paidAmount=" + getPaidAmount() +
            ", balanceType='" + getBalanceType() + "'" +
            ", billClosingFlag='" + getBillClosingFlag() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", vatNo='" + getVatNo() + "'" +
            ", contCode='" + getContCode() + "'" +
            ", orderNo='" + getOrderNo() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
