package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.ReservedFlag;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.BalanceType;

import org.soptorshi.domain.enumeration.ReservedFlag;

import org.soptorshi.domain.enumeration.DepreciationType;

/**
 * A MstAccount.
 */
@Entity
@Table(name = "mst_account")
@Document(indexName = "mstaccount")
@EntityListeners(AuditingEntityListener.class)
public class MstAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "year_open_balance", precision = 10, scale = 2)
    private BigDecimal yearOpenBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "year_open_balance_type")
    private BalanceType yearOpenBalanceType;

    @Column(name = "year_close_balance", precision = 10, scale = 2)
    private BigDecimal yearCloseBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "reserved_flag")
    private ReservedFlag reservedFlag;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_on")
    @LastModifiedDate
    private LocalDate modifiedOn;

    @Column(name = "depreciation_rate", precision = 10, scale = 2)
    private BigDecimal depreciationRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "depreciation_type")
    private DepreciationType depreciationType;

    @ManyToOne
    @JsonIgnoreProperties("mstAccounts")
    private MstGroup group;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public MstAccount code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public MstAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getYearOpenBalance() {
        return yearOpenBalance;
    }

    public MstAccount yearOpenBalance(BigDecimal yearOpenBalance) {
        this.yearOpenBalance = yearOpenBalance;
        return this;
    }

    public void setYearOpenBalance(BigDecimal yearOpenBalance) {
        this.yearOpenBalance = yearOpenBalance;
    }

    public BalanceType getYearOpenBalanceType() {
        return yearOpenBalanceType;
    }

    public MstAccount yearOpenBalanceType(BalanceType yearOpenBalanceType) {
        this.yearOpenBalanceType = yearOpenBalanceType;
        return this;
    }

    public void setYearOpenBalanceType(BalanceType yearOpenBalanceType) {
        this.yearOpenBalanceType = yearOpenBalanceType;
    }

    public BigDecimal getYearCloseBalance() {
        return yearCloseBalance;
    }

    public MstAccount yearCloseBalance(BigDecimal yearCloseBalance) {
        this.yearCloseBalance = yearCloseBalance;
        return this;
    }

    public void setYearCloseBalance(BigDecimal yearCloseBalance) {
        this.yearCloseBalance = yearCloseBalance;
    }

    public ReservedFlag getReservedFlag() {
        return reservedFlag;
    }

    public MstAccount reservedFlag(ReservedFlag reservedFlag) {
        this.reservedFlag = reservedFlag;
        return this;
    }

    public void setReservedFlag(ReservedFlag reservedFlag) {
        this.reservedFlag = reservedFlag;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public MstAccount modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public MstAccount modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public BigDecimal getDepreciationRate() {
        return depreciationRate;
    }

    public MstAccount depreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
        return this;
    }

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public DepreciationType getDepreciationType() {
        return depreciationType;
    }

    public MstAccount depreciationType(DepreciationType depreciationType) {
        this.depreciationType = depreciationType;
        return this;
    }

    public void setDepreciationType(DepreciationType depreciationType) {
        this.depreciationType = depreciationType;
    }

    public MstGroup getGroup() {
        return group;
    }

    public MstAccount group(MstGroup mstGroup) {
        this.group = mstGroup;
        return this;
    }

    public void setGroup(MstGroup mstGroup) {
        this.group = mstGroup;
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
        MstAccount mstAccount = (MstAccount) o;
        if (mstAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mstAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MstAccount{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", yearOpenBalance=" + getYearOpenBalance() +
            ", yearOpenBalanceType='" + getYearOpenBalanceType() + "'" +
            ", yearCloseBalance=" + getYearCloseBalance() +
            ", reservedFlag='" + getReservedFlag() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", depreciationRate=" + getDepreciationRate() +
            ", depreciationType='" + getDepreciationType() + "'" +
            "}";
    }
}
