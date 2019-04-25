package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.EmploymentType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the ExperienceInformation entity. This class is used in ExperienceInformationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /experience-informations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExperienceInformationCriteria implements Serializable {
    /**
     * Class for filtering EmploymentType
     */
    public static class EmploymentTypeFilter extends Filter<EmploymentType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter organization;

    private StringFilter designation;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private EmploymentTypeFilter employmentType;

    private LongFilter employeeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOrganization() {
        return organization;
    }

    public void setOrganization(StringFilter organization) {
        this.organization = organization;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public EmploymentTypeFilter getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentTypeFilter employmentType) {
        this.employmentType = employmentType;
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
        final ExperienceInformationCriteria that = (ExperienceInformationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(organization, that.organization) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(employmentType, that.employmentType) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        organization,
        designation,
        startDate,
        endDate,
        employmentType,
        employeeId
        );
    }

    @Override
    public String toString() {
        return "ExperienceInformationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (organization != null ? "organization=" + organization + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (employmentType != null ? "employmentType=" + employmentType + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
