package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.CommercialPaymentStatus;
import org.soptorshi.domain.enumeration.PaymentType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the CommercialPaymentInfo entity.
 */
public class CommercialPaymentInfoDTO implements Serializable {

    private Long id;

    private PaymentType paymentType;

    @NotNull
    private BigDecimal totalAmountToPay;

    @NotNull
    private BigDecimal totalAmountPaid;

    @NotNull
    private BigDecimal remainingAmountToPay;

    private CommercialPaymentStatus paymentStatus;

    private Instant createdOn;

    private String createdBy;

    private Instant updatedOn;

    private String updatedBy;


    private Long commercialPiId;

    private String commercialPiProformaNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getTotalAmountToPay() {
        return totalAmountToPay;
    }

    public void setTotalAmountToPay(BigDecimal totalAmountToPay) {
        this.totalAmountToPay = totalAmountToPay;
    }

    public BigDecimal getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    public BigDecimal getRemainingAmountToPay() {
        return remainingAmountToPay;
    }

    public void setRemainingAmountToPay(BigDecimal remainingAmountToPay) {
        this.remainingAmountToPay = remainingAmountToPay;
    }

    public CommercialPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(CommercialPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getCommercialPiId() {
        return commercialPiId;
    }

    public void setCommercialPiId(Long commercialPiId) {
        this.commercialPiId = commercialPiId;
    }

    public String getCommercialPiProformaNo() {
        return commercialPiProformaNo;
    }

    public void setCommercialPiProformaNo(String commercialPiProformaNo) {
        this.commercialPiProformaNo = commercialPiProformaNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialPaymentInfoDTO commercialPaymentInfoDTO = (CommercialPaymentInfoDTO) o;
        if (commercialPaymentInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPaymentInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPaymentInfoDTO{" +
            "id=" + getId() +
            ", paymentType='" + getPaymentType() + "'" +
            ", totalAmountToPay=" + getTotalAmountToPay() +
            ", totalAmountPaid=" + getTotalAmountPaid() +
            ", remainingAmountToPay=" + getRemainingAmountToPay() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", commercialPi=" + getCommercialPiId() +
            ", commercialPi='" + getCommercialPiProformaNo() + "'" +
            "}";
    }
}
