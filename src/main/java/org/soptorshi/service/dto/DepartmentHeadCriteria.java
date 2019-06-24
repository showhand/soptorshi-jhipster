package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the DepartmentHead entity. This class is used in DepartmentHeadResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /department-heads?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepartmentHeadCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter departmentId;

    private LongFilter employeeId;

    private LongFilter officeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getOfficeId() {
        return officeId;
    }

    public void setOfficeId(LongFilter officeId) {
        this.officeId = officeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepartmentHeadCriteria that = (DepartmentHeadCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(officeId, that.officeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        departmentId,
        employeeId,
        officeId
        );
    }

    @Override
    public String toString() {
        return "DepartmentHeadCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (officeId != null ? "officeId=" + officeId + ", " : "") +
            "}";
    }

}
