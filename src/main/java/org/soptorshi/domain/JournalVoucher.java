package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.VoucherType;

import org.soptorshi.domain.enumeration.VoucherReferenceType;

import org.soptorshi.domain.enumeration.ApplicationType;

/**
 * A JournalVoucher.
 */
@Entity
@Table(name = "journal_voucher")
@Document(indexName = "journalvoucher")
public class JournalVoucher implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_no")
    private String voucherNo;

    @Column(name = "voucher_date")
    private LocalDate voucherDate;

    @Column(name = "post_date")
    private LocalDate postDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private VoucherType type;

    @Column(name = "conversion_factor", precision = 10, scale = 2)
    private BigDecimal conversionFactor;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference")
    private VoucherReferenceType reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_type")
    private ApplicationType applicationType;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("journalVouchers")
    private Currency currency;

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

    public JournalVoucher voucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
        return this;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public JournalVoucher voucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
        return this;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public JournalVoucher postDate(LocalDate postDate) {
        this.postDate = postDate;
        return this;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public VoucherType getType() {
        return type;
    }

    public JournalVoucher type(VoucherType type) {
        this.type = type;
        return this;
    }

    public void setType(VoucherType type) {
        this.type = type;
    }

    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }

    public JournalVoucher conversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
        return this;
    }

    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public VoucherReferenceType getReference() {
        return reference;
    }

    public JournalVoucher reference(VoucherReferenceType reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(VoucherReferenceType reference) {
        this.reference = reference;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public JournalVoucher applicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
        return this;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public JournalVoucher applicationId(Long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public JournalVoucher referenceId(Long referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public JournalVoucher modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public JournalVoucher modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Currency getCurrency() {
        return currency;
    }

    public JournalVoucher currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
        JournalVoucher journalVoucher = (JournalVoucher) o;
        if (journalVoucher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journalVoucher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JournalVoucher{" +
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
            "}";
    }
}
