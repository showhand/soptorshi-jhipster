package org.soptorshi.domain;


import org.soptorshi.domain.enumeration.DayOfWeek;
import org.soptorshi.domain.enumeration.WeekendStatus;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Weekend.
 */
@Entity
@Table(name = "weekend")
@Document(indexName = "weekend")
public class Weekend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    @Column(name = "number_of_days", nullable = false)
    private Integer numberOfDays;

    @Column(name = "active_from")
    private LocalDate activeFrom;

    @Column(name = "active_to")
    private LocalDate activeTo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day_1", nullable = false)
    private DayOfWeek day1;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_2")
    private DayOfWeek day2;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_3")
    private DayOfWeek day3;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WeekendStatus status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public Weekend numberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public LocalDate getActiveFrom() {
        return activeFrom;
    }

    public Weekend activeFrom(LocalDate activeFrom) {
        this.activeFrom = activeFrom;
        return this;
    }

    public void setActiveFrom(LocalDate activeFrom) {
        this.activeFrom = activeFrom;
    }

    public LocalDate getActiveTo() {
        return activeTo;
    }

    public Weekend activeTo(LocalDate activeTo) {
        this.activeTo = activeTo;
        return this;
    }

    public void setActiveTo(LocalDate activeTo) {
        this.activeTo = activeTo;
    }

    public DayOfWeek getDay1() {
        return day1;
    }

    public Weekend day1(DayOfWeek day1) {
        this.day1 = day1;
        return this;
    }

    public void setDay1(DayOfWeek day1) {
        this.day1 = day1;
    }

    public DayOfWeek getDay2() {
        return day2;
    }

    public Weekend day2(DayOfWeek day2) {
        this.day2 = day2;
        return this;
    }

    public void setDay2(DayOfWeek day2) {
        this.day2 = day2;
    }

    public DayOfWeek getDay3() {
        return day3;
    }

    public Weekend day3(DayOfWeek day3) {
        this.day3 = day3;
        return this;
    }

    public void setDay3(DayOfWeek day3) {
        this.day3 = day3;
    }

    public WeekendStatus getStatus() {
        return status;
    }

    public Weekend status(WeekendStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(WeekendStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Weekend createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Weekend createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Weekend updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Weekend updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
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
        Weekend weekend = (Weekend) o;
        if (weekend.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), weekend.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Weekend{" +
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
