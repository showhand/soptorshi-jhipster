package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.HolidayImposedAuthority;
import org.soptorshi.domain.enumeration.YesOrNo;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Holiday.
 */
@Entity
@Table(name = "holiday")
@Document(indexName = "holiday")
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @NotNull
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @NotNull
    @Column(name = "number_of_days", nullable = false)
    private Integer numberOfDays;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "moon_dependency", nullable = false)
    private YesOrNo moonDependency;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "holiday_declared_by", nullable = false)
    private HolidayImposedAuthority holidayDeclaredBy;

    @Lob
    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "holiday_year")
    private Integer holidayYear;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("holidays")
    private HolidayType holidayType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public Holiday fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public Holiday toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public Holiday numberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public YesOrNo getMoonDependency() {
        return moonDependency;
    }

    public Holiday moonDependency(YesOrNo moonDependency) {
        this.moonDependency = moonDependency;
        return this;
    }

    public void setMoonDependency(YesOrNo moonDependency) {
        this.moonDependency = moonDependency;
    }

    public HolidayImposedAuthority getHolidayDeclaredBy() {
        return holidayDeclaredBy;
    }

    public Holiday holidayDeclaredBy(HolidayImposedAuthority holidayDeclaredBy) {
        this.holidayDeclaredBy = holidayDeclaredBy;
        return this;
    }

    public void setHolidayDeclaredBy(HolidayImposedAuthority holidayDeclaredBy) {
        this.holidayDeclaredBy = holidayDeclaredBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public Holiday remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Holiday createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Holiday createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Holiday updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Holiday updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getHolidayYear() {
        return holidayYear;
    }

    public Holiday holidayYear(Integer holidayYear) {
        this.holidayYear = holidayYear;
        return this;
    }

    public void setHolidayYear(Integer holidayYear) {
        this.holidayYear = holidayYear;
    }

    public HolidayType getHolidayType() {
        return holidayType;
    }

    public Holiday holidayType(HolidayType holidayType) {
        this.holidayType = holidayType;
        return this;
    }

    public void setHolidayType(HolidayType holidayType) {
        this.holidayType = holidayType;
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
        Holiday holiday = (Holiday) o;
        if (holiday.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), holiday.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Holiday{" +
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
            "}";
    }
}
