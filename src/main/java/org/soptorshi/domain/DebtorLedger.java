package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.BillClosingFlag;

/**
 * A DebtorLedger.
 */
@Entity
@Table(name = "debtor_ledger")
@Document(indexName = "debtorledger")
public class DebtorLedger implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_no")
    private String serialNo;

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "bill_date")
    private LocalDate billDate;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "paid_amount", precision = 10, scale = 2)
    private BigDecimal paidAmount;

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
    @JsonIgnoreProperties("debtorLedgers")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public DebtorLedger serialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public DebtorLedger billNo(String billNo) {
        this.billNo = billNo;
        return this;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public DebtorLedger billDate(LocalDate billDate) {
        this.billDate = billDate;
        return this;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public DebtorLedger amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public DebtorLedger paidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
        return this;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BillClosingFlag getBillClosingFlag() {
        return billClosingFlag;
    }

    public DebtorLedger billClosingFlag(BillClosingFlag billClosingFlag) {
        this.billClosingFlag = billClosingFlag;
        return this;
    }

    public void setBillClosingFlag(BillClosingFlag billClosingFlag) {
        this.billClosingFlag = billClosingFlag;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public DebtorLedger dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getVatNo() {
        return vatNo;
    }

    public DebtorLedger vatNo(String vatNo) {
        this.vatNo = vatNo;
        return this;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getContCode() {
        return contCode;
    }

    public DebtorLedger contCode(String contCode) {
        this.contCode = contCode;
        return this;
    }

    public void setContCode(String contCode) {
        this.contCode = contCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public DebtorLedger orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public DebtorLedger modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public DebtorLedger modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Customer getCustomer() {
        return customer;
    }

    public DebtorLedger customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        DebtorLedger debtorLedger = (DebtorLedger) o;
        if (debtorLedger.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), debtorLedger.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DebtorLedger{" +
            "id=" + getId() +
            ", serialNo='" + getSerialNo() + "'" +
            ", billNo='" + getBillNo() + "'" +
            ", billDate='" + getBillDate() + "'" +
            ", amount=" + getAmount() +
            ", paidAmount=" + getPaidAmount() +
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
