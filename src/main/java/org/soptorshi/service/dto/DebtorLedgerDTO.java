package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.BillClosingFlag;

/**
 * A DTO for the DebtorLedger entity.
 */
public class DebtorLedgerDTO implements Serializable {

    private Long id;

    private String serialNo;

    private String billNo;

    private LocalDate billDate;

    private BigDecimal amount;

    private BigDecimal paidAmount;

    private BillClosingFlag billClosingFlag;

    private LocalDate dueDate;

    private String vatNo;

    private String contCode;

    private String orderNo;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long customerId;

    private String customerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BillClosingFlag getBillClosingFlag() {
        return billClosingFlag;
    }

    public void setBillClosingFlag(BillClosingFlag billClosingFlag) {
        this.billClosingFlag = billClosingFlag;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getContCode() {
        return contCode;
    }

    public void setContCode(String contCode) {
        this.contCode = contCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DebtorLedgerDTO debtorLedgerDTO = (DebtorLedgerDTO) o;
        if (debtorLedgerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), debtorLedgerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DebtorLedgerDTO{" +
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
            ", customer=" + getCustomerId() +
            ", customer='" + getCustomerName() + "'" +
            "}";
    }
}
