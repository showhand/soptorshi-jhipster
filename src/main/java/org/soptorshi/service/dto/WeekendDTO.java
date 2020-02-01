package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.DayOfWeek;
import org.soptorshi.domain.enumeration.WeekendStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Weekend entity.
 */
public class WeekendDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    private Integer numberOfDays;

    private LocalDate activeFrom;

    private LocalDate activeTo;

    @NotNull
    private DayOfWeek day1;

    private DayOfWeek day2;

    private DayOfWeek day3;

    @NotNull
    private WeekendStatus status;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public LocalDate getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(LocalDate activeFrom) {
        this.activeFrom = activeFrom;
    }

    public LocalDate getActiveTo() {
        return activeTo;
    }

    public void setActiveTo(LocalDate activeTo) {
        this.activeTo = activeTo;
    }

    public DayOfWeek getDay1() {
        return day1;
    }

    public void setDay1(DayOfWeek day1) {
        this.day1 = day1;
    }

    public DayOfWeek getDay2() {
        return day2;
    }

    public void setDay2(DayOfWeek day2) {
        this.day2 = day2;
    }

    public DayOfWeek getDay3() {
        return day3;
    }

    public void setDay3(DayOfWeek day3) {
        this.day3 = day3;
    }

    public WeekendStatus getStatus() {
        return status;
    }

    public void setStatus(WeekendStatus status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeekendDTO weekendDTO = (WeekendDTO) o;
        if (weekendDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weekendDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WeekendDTO{" +
            "id=" + getId() +
            ", numberOfDays=" + getNumberOfDays() +
            ", activeFrom='" + getActiveFrom() + "'" +
            ", activeTo='" + getActiveTo() + "'" +
            ", day1='" + getDay1() + "'" +
            ", day2='" + getDay2() + "'" +
            ", day3='" + getDay3() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
