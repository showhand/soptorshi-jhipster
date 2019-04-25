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
 * Criteria class for the AcademicInformation entity. This class is used in AcademicInformationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /academic-informations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AcademicInformationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter degree;

    private StringFilter boardOrUniversity;

    private IntegerFilter passingYear;

    private StringFilter group;

    private LongFilter employeeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDegree() {
        return degree;
    }

    public void setDegree(StringFilter degree) {
        this.degree = degree;
    }

    public StringFilter getBoardOrUniversity() {
        return boardOrUniversity;
    }

    public void setBoardOrUniversity(StringFilter boardOrUniversity) {
        this.boardOrUniversity = boardOrUniversity;
    }

    public IntegerFilter getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(IntegerFilter passingYear) {
        this.passingYear = passingYear;
    }

    public StringFilter getGroup() {
        return group;
    }

    public void setGroup(StringFilter group) {
        this.group = group;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AcademicInformationCriteria that = (AcademicInformationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(degree, that.degree) &&
            Objects.equals(boardOrUniversity, that.boardOrUniversity) &&
            Objects.equals(passingYear, that.passingYear) &&
            Objects.equals(group, that.group) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        degree,
        boardOrUniversity,
        passingYear,
        group,
        employeeId
        );
    }

    @Override
    public String toString() {
        return "AcademicInformationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (degree != null ? "degree=" + degree + ", " : "") +
                (boardOrUniversity != null ? "boardOrUniversity=" + boardOrUniversity + ", " : "") +
                (passingYear != null ? "passingYear=" + passingYear + ", " : "") +
                (group != null ? "group=" + group + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
