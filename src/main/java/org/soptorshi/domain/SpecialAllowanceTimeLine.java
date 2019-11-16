package org.soptorshi.domain;



import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.AllowanceType;

import org.soptorshi.domain.enumeration.Religion;

import org.soptorshi.domain.enumeration.MonthType;

/**
 * A SpecialAllowanceTimeLine.
 */
@Entity
@Table(name = "special_allowance_time_line")
@Document(indexName = "specialallowancetimeline")
public class SpecialAllowanceTimeLine implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "allowance_type")
    private AllowanceType allowanceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "religion")
    private Religion religion;

    @Column(name = "jhi_year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private MonthType month;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AllowanceType getAllowanceType() {
        return allowanceType;
    }

    public SpecialAllowanceTimeLine allowanceType(AllowanceType allowanceType) {
        this.allowanceType = allowanceType;
        return this;
    }

    public void setAllowanceType(AllowanceType allowanceType) {
        this.allowanceType = allowanceType;
    }

    public Religion getReligion() {
        return religion;
    }

    public SpecialAllowanceTimeLine religion(Religion religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public Integer getYear() {
        return year;
    }

    public SpecialAllowanceTimeLine year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MonthType getMonth() {
        return month;
    }

    public SpecialAllowanceTimeLine month(MonthType month) {
        this.month = month;
        return this;
    }

    public void setMonth(MonthType month) {
        this.month = month;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public SpecialAllowanceTimeLine modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public SpecialAllowanceTimeLine modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
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
        SpecialAllowanceTimeLine specialAllowanceTimeLine = (SpecialAllowanceTimeLine) o;
        if (specialAllowanceTimeLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialAllowanceTimeLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpecialAllowanceTimeLine{" +
            "id=" + getId() +
            ", allowanceType='" + getAllowanceType() + "'" +
            ", religion='" + getReligion() + "'" +
            ", year=" + getYear() +
            ", month='" + getMonth() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
