package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import org.soptorshi.domain.enumeration.PaymentStatus;

/**
 * A DTO for the Advance entity.
 */
public class AdvanceDTO implements Serializable {

    private Long id;

    private BigDecimal amount;

    @Lob
    private byte[] reason;

    private String reasonContentType;
    private LocalDate providedOn;

    private PaymentStatus paymentStatus;

    private BigDecimal left;

    private Long modifiedBy;

    private LocalDate modifiedOn;


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

    public LocalDate getProvidedOn() {
        return providedOn;
    }

    public void setProvidedOn(LocalDate providedOn) {
        this.providedOn = providedOn;
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

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
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

        AdvanceDTO advanceDTO = (AdvanceDTO) o;
        if (advanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), advanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdvanceDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", reason='" + getReason() + "'" +
            ", providedOn='" + getProvidedOn() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", left=" + getLeft() +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
