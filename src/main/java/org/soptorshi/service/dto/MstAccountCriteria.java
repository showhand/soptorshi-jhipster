package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.ReservedFlag;
import org.soptorshi.domain.enumeration.DepreciationType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the MstAccount entity. This class is used in MstAccountResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /mst-accounts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MstAccountCriteria implements Serializable {
    /**
     * Class for filtering BalanceType
     */
    public static class BalanceTypeFilter extends Filter<BalanceType> {
    }
    /**
     * Class for filtering ReservedFlag
     */
    public static class ReservedFlagFilter extends Filter<ReservedFlag> {
    }
    /**
     * Class for filtering DepreciationType
     */
    public static class DepreciationTypeFilter extends Filter<DepreciationType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter name;

    private BigDecimalFilter yearOpenBalance;

    private BalanceTypeFilter yearOpenBalanceType;

    private BigDecimalFilter yearCloseBalance;

    private ReservedFlagFilter reservedFlag;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private BigDecimalFilter depreciationRate;

    private DepreciationTypeFilter depreciationType;

    private LongFilter groupId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BigDecimalFilter getYearOpenBalance() {
        return yearOpenBalance;
    }

    public void setYearOpenBalance(BigDecimalFilter yearOpenBalance) {
        this.yearOpenBalance = yearOpenBalance;
    }

    public BalanceTypeFilter getYearOpenBalanceType() {
        return yearOpenBalanceType;
    }

    public void setYearOpenBalanceType(BalanceTypeFilter yearOpenBalanceType) {
        this.yearOpenBalanceType = yearOpenBalanceType;
    }

    public BigDecimalFilter getYearCloseBalance() {
        return yearCloseBalance;
    }

    public void setYearCloseBalance(BigDecimalFilter yearCloseBalance) {
        this.yearCloseBalance = yearCloseBalance;
    }

    public ReservedFlagFilter getReservedFlag() {
        return reservedFlag;
    }

    public void setReservedFlag(ReservedFlagFilter reservedFlag) {
        this.reservedFlag = reservedFlag;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateFilter getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateFilter modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public BigDecimalFilter getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(BigDecimalFilter depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public DepreciationTypeFilter getDepreciationType() {
        return depreciationType;
    }

    public void setDepreciationType(DepreciationTypeFilter depreciationType) {
        this.depreciationType = depreciationType;
    }

    public LongFilter getGroupId() {
        return groupId;
    }

    public void setGroupId(LongFilter groupId) {
        this.groupId = groupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MstAccountCriteria that = (MstAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(yearOpenBalance, that.yearOpenBalance) &&
            Objects.equals(yearOpenBalanceType, that.yearOpenBalanceType) &&
            Objects.equals(yearCloseBalance, that.yearCloseBalance) &&
            Objects.equals(reservedFlag, that.reservedFlag) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(depreciationRate, that.depreciationRate) &&
            Objects.equals(depreciationType, that.depreciationType) &&
            Objects.equals(groupId, that.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        name,
        yearOpenBalance,
        yearOpenBalanceType,
        yearCloseBalance,
        reservedFlag,
        modifiedBy,
        modifiedOn,
        depreciationRate,
        depreciationType,
        groupId
        );
    }

    @Override
    public String toString() {
        return "MstAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (yearOpenBalance != null ? "yearOpenBalance=" + yearOpenBalance + ", " : "") +
                (yearOpenBalanceType != null ? "yearOpenBalanceType=" + yearOpenBalanceType + ", " : "") +
                (yearCloseBalance != null ? "yearCloseBalance=" + yearCloseBalance + ", " : "") +
                (reservedFlag != null ? "reservedFlag=" + reservedFlag + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (depreciationRate != null ? "depreciationRate=" + depreciationRate + ", " : "") +
                (depreciationType != null ? "depreciationType=" + depreciationType + ", " : "") +
                (groupId != null ? "groupId=" + groupId + ", " : "") +
            "}";
    }

}
