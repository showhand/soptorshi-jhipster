package org.soptorshi.domain;



import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DepreciationMap.
 */
@Entity
@Table(name = "depreciation_map")
@Document(indexName = "depreciationmap")
@EntityListeners(AuditingEntityListener.class)
public class DepreciationMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "depreciation_account_id")
    private Long depreciationAccountId;

    @Column(name = "depreciation_account_name")
    private String depreciationAccountName;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_on")
    @CreatedDate
    private Instant createdOn;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_on")
    @LastModifiedDate
    private Instant modifiedOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepreciationAccountId() {
        return depreciationAccountId;
    }

    public DepreciationMap depreciationAccountId(Long depreciationAccountId) {
        this.depreciationAccountId = depreciationAccountId;
        return this;
    }

    public void setDepreciationAccountId(Long depreciationAccountId) {
        this.depreciationAccountId = depreciationAccountId;
    }

    public String getDepreciationAccountName() {
        return depreciationAccountName;
    }

    public DepreciationMap depreciationAccountName(String depreciationAccountName) {
        this.depreciationAccountName = depreciationAccountName;
        return this;
    }

    public void setDepreciationAccountName(String depreciationAccountName) {
        this.depreciationAccountName = depreciationAccountName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public DepreciationMap accountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public DepreciationMap accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public DepreciationMap createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public DepreciationMap createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public DepreciationMap modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedOn() {
        return modifiedOn;
    }

    public DepreciationMap modifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
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
        DepreciationMap depreciationMap = (DepreciationMap) o;
        if (depreciationMap.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depreciationMap.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepreciationMap{" +
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
