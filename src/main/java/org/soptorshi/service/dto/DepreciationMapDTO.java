package org.soptorshi.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DepreciationMap entity.
 */
public class DepreciationMapDTO implements Serializable {

    private Long id;

    private Long depreciationAccountId;

    private String depreciationAccountName;

    private Long accountId;

    private String accountName;

    private String createdBy;

    private Instant createdOn;

    private String modifiedBy;

    private Instant modifiedOn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepreciationAccountId() {
        return depreciationAccountId;
    }

    public void setDepreciationAccountId(Long depreciationAccountId) {
        this.depreciationAccountId = depreciationAccountId;
    }

    public String getDepreciationAccountName() {
        return depreciationAccountName;
    }

    public void setDepreciationAccountName(String depreciationAccountName) {
        this.depreciationAccountName = depreciationAccountName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepreciationMapDTO depreciationMapDTO = (DepreciationMapDTO) o;
        if (depreciationMapDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depreciationMapDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepreciationMapDTO{" +
            "id=" + getId() +
            ", depreciationAccountId=" + getDepreciationAccountId() +
            ", depreciationAccountName='" + getDepreciationAccountName() + "'" +
            ", accountId=" + getAccountId() +
            ", accountName='" + getAccountName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
