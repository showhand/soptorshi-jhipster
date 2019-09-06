package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.AccountType;

/**
 * A DTO for the SystemAccountMap entity.
 */
public class SystemAccountMapDTO implements Serializable {

    private Long id;

    private AccountType accountType;

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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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

        SystemAccountMapDTO systemAccountMapDTO = (SystemAccountMapDTO) o;
        if (systemAccountMapDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemAccountMapDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemAccountMapDTO{" +
            "id=" + getId() +
            ", accountType='" + getAccountType() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", account=" + getAccountId() +
            ", account='" + getAccountName() + "'" +
            "}";
    }
}
