package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.AllowanceType;
import org.soptorshi.domain.enumeration.Religion;
import org.soptorshi.domain.enumeration.MonthType;

/**
 * A DTO for the SpecialAllowanceTimeLine entity.
 */
public class SpecialAllowanceTimeLineDTO implements Serializable {

    private Long id;

    private AllowanceType allowanceType;

    private Religion religion;

    private Integer year;

    private MonthType month;

    private String modifiedBy;

    private LocalDate modifiedOn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AllowanceType getAllowanceType() {
        return allowanceType;
    }

    public void setAllowanceType(AllowanceType allowanceType) {
        this.allowanceType = allowanceType;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MonthType getMonth() {
        return month;
    }

    public void setMonth(MonthType month) {
        this.month = month;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
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

        SpecialAllowanceTimeLineDTO specialAllowanceTimeLineDTO = (SpecialAllowanceTimeLineDTO) o;
        if (specialAllowanceTimeLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialAllowanceTimeLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpecialAllowanceTimeLineDTO{" +
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
