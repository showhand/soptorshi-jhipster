package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.AttendanceType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the AttendanceExcelUpload entity. This class is used in AttendanceExcelUploadResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /attendance-excel-uploads?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AttendanceExcelUploadCriteria implements Serializable {
    /**
     * Class for filtering AttendanceType
     */
    public static class AttendanceTypeFilter extends Filter<AttendanceType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AttendanceTypeFilter type;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public AttendanceTypeFilter getType() {
        return type;
    }

    public void setType(AttendanceTypeFilter type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceExcelUploadCriteria that = (AttendanceExcelUploadCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type
        );
    }

    @Override
    public String toString() {
        return "AttendanceExcelUploadCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
            "}";
    }

}
