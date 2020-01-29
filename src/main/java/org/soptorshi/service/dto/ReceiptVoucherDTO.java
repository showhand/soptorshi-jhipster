package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.ApplicationType;

/**
 * A DTO for the ReceiptVoucher entity.
 */
public class ReceiptVoucherDTO implements Serializable {

    private Long id;

    private String voucherNo;

    private LocalDate voucherDate;

    private LocalDate postDate;

    private ApplicationType applicationType;

    private Long applicationId;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long accountId;

    private String accountName;

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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long mstAccountId) {
        this.accountId = mstAccountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String mstAccountName) {
        this.accountName = mstAccountName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReceiptVoucherDTO receiptVoucherDTO = (ReceiptVoucherDTO) o;
        if (receiptVoucherDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), receiptVoucherDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReceiptVoucherDTO{" +
            "id=" + getId() +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", postDate='" + getPostDate() + "'" +
            ", applicationType='" + getApplicationType() + "'" +
            ", applicationId=" + getApplicationId() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", account=" + getAccountId() +
            ", account='" + getAccountName() + "'" +
            "}";
    }
}
