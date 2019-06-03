package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import org.soptorshi.domain.enumeration.PaymentStatus;

/**
 * A DTO for the Fine entity.
 */
public class FineDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal amount;

    
    @Lob
    private byte[] reason;

    private String reasonContentType;
    @NotNull
    private LocalDate fineDate;

    private Double monthlyPayable;

    private PaymentStatus paymentStatus;

    private BigDecimal left;

    private String modifiedBy;

    private LocalDate modifiedDate;


    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public byte[] getReason() {
        return reason;
    }

    public void setReason(byte[] reason) {
        this.reason = reason;
    }

    public String getReasonContentType() {
        return reasonContentType;
    }

    public void setReasonContentType(String reasonContentType) {
        this.reasonContentType = reasonContentType;
    }

    public LocalDate getFineDate() {
        return fineDate;
    }

    public void setFineDate(LocalDate fineDate) {
        this.fineDate = fineDate;
    }

    public Double getMonthlyPayable() {
        return monthlyPayable;
    }

    public void setMonthlyPayable(Double monthlyPayable) {
        this.monthlyPayable = monthlyPayable;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getLeft() {
        return left;
    }

    public void setLeft(BigDecimal left) {
        this.left = left;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FineDTO fineDTO = (FineDTO) o;
        if (fineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FineDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", reason='" + getReason() + "'" +
            ", fineDate='" + getFineDate() + "'" +
            ", monthlyPayable=" + getMonthlyPayable() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", left=" + getLeft() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
