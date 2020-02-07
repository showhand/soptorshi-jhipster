package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.HolidayImposedAuthority;
import org.soptorshi.domain.enumeration.YesOrNo;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Holiday entity.
 */
public class HolidayDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    @NotNull
    private Integer numberOfDays;

    @NotNull
    private YesOrNo moonDependency;

    @NotNull
    private HolidayImposedAuthority holidayDeclaredBy;

    @Lob
    private String remarks;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private Integer holidayYear;


    private Long holidayTypeId;

    private String holidayTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public YesOrNo getMoonDependency() {
        return moonDependency;
    }

    public void setMoonDependency(YesOrNo moonDependency) {
        this.moonDependency = moonDependency;
    }

    public HolidayImposedAuthority getHolidayDeclaredBy() {
        return holidayDeclaredBy;
    }

    public void setHolidayDeclaredBy(HolidayImposedAuthority holidayDeclaredBy) {
        this.holidayDeclaredBy = holidayDeclaredBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getHolidayYear() {
        return holidayYear;
    }

    public void setHolidayYear(Integer holidayYear) {
        this.holidayYear = holidayYear;
    }

    public Long getHolidayTypeId() {
        return holidayTypeId;
    }

    public void setHolidayTypeId(Long holidayTypeId) {
        this.holidayTypeId = holidayTypeId;
    }

    public String getHolidayTypeName() {
        return holidayTypeName;
    }

    public void setHolidayTypeName(String holidayTypeName) {
        this.holidayTypeName = holidayTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HolidayDTO holidayDTO = (HolidayDTO) o;
        if (holidayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), holidayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HolidayDTO{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", numberOfDays=" + getNumberOfDays() +
            ", moonDependency='" + getMoonDependency() + "'" +
            ", holidayDeclaredBy='" + getHolidayDeclaredBy() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", holidayYear=" + getHolidayYear() +
            ", holidayType=" + getHolidayTypeId() +
            ", holidayType='" + getHolidayTypeName() + "'" +
            "}";
    }
}
