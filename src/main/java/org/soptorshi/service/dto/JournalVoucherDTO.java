package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.VoucherType;
import org.soptorshi.domain.enumeration.VoucherReferenceType;
import org.soptorshi.domain.enumeration.ApplicationType;

/**
 * A DTO for the JournalVoucher entity.
 */
public class JournalVoucherDTO implements Serializable {

    private Long id;

    private String voucherNo;

    private LocalDate voucherDate;

    private LocalDate postDate;

    private VoucherType type;

    private BigDecimal conversionFactor;

    private VoucherReferenceType reference;

    private ApplicationType applicationType;

    private Long applicationId;

    private Long referenceId;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long currencyId;

    private String currencyNotation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public VoucherType getType() {
        return type;
    }

    public void setType(VoucherType type) {
        this.type = type;
    }

    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public VoucherReferenceType getReference() {
        return reference;
    }

    public void setReference(VoucherReferenceType reference) {
        this.reference = reference;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
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

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyNotation() {
        return currencyNotation;
    }

    public void setCurrencyNotation(String currencyNotation) {
        this.currencyNotation = currencyNotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JournalVoucherDTO journalVoucherDTO = (JournalVoucherDTO) o;
        if (journalVoucherDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journalVoucherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JournalVoucherDTO{" +
            "id=" + getId() +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", postDate='" + getPostDate() + "'" +
            ", type='" + getType() + "'" +
            ", conversionFactor=" + getConversionFactor() +
            ", reference='" + getReference() + "'" +
            ", applicationType='" + getApplicationType() + "'" +
            ", applicationId=" + getApplicationId() +
            ", referenceId=" + getReferenceId() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", currency=" + getCurrencyId() +
            ", currency='" + getCurrencyNotation() + "'" +
            "}";
    }
}
