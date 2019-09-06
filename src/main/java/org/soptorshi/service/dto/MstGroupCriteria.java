package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.ReservedFlag;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the MstGroup entity. This class is used in MstGroupResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /mst-groups?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MstGroupCriteria implements Serializable {
    /**
     * Class for filtering ReservedFlag
     */
    public static class ReservedFlagFilter extends Filter<ReservedFlag> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter mainGroup;

    private ReservedFlagFilter reservedFlag;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getMainGroup() {
        return mainGroup;
    }

    public void setMainGroup(LongFilter mainGroup) {
        this.mainGroup = mainGroup;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MstGroupCriteria that = (MstGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(mainGroup, that.mainGroup) &&
            Objects.equals(reservedFlag, that.reservedFlag) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        mainGroup,
        reservedFlag,
        modifiedBy,
        modifiedOn
        );
    }

    @Override
    public String toString() {
        return "MstGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (mainGroup != null ? "mainGroup=" + mainGroup + ", " : "") +
                (reservedFlag != null ? "reservedFlag=" + reservedFlag + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
            "}";
    }

}
