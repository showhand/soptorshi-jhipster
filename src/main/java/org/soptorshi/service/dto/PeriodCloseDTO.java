package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.PeriodCloseFlag;

/**
 * A DTO for the PeriodClose entity.
 */
public class PeriodCloseDTO implements Serializable {

    private Long id;

    private MonthType monthType;

    private Integer closeYear;

    private PeriodCloseFlag flag;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long financialAccountYearId;

    private String financialAccountYearDurationStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonthType getMonthType() {
        return monthType;
    }

    public void setMonthType(MonthType monthType) {
        this.monthType = monthType;
    }

    public Integer getCloseYear() {
        return closeYear;
    }

    public void setCloseYear(Integer closeYear) {
        this.closeYear = closeYear;
    }

    public PeriodCloseFlag getFlag() {
        return flag;
    }

    public void setFlag(PeriodCloseFlag flag) {
        this.flag = flag;
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

    public Long getFinancialAccountYearId() {
        return financialAccountYearId;
    }

    public void setFinancialAccountYearId(Long financialAccountYearId) {
        this.financialAccountYearId = financialAccountYearId;
    }

    public String getFinancialAccountYearDurationStr() {
        return financialAccountYearDurationStr;
    }

    public void setFinancialAccountYearDurationStr(String financialAccountYearDurationStr) {
        this.financialAccountYearDurationStr = financialAccountYearDurationStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PeriodCloseDTO periodCloseDTO = (PeriodCloseDTO) o;
        if (periodCloseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), periodCloseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PeriodCloseDTO{" +
            "id=" + getId() +
            ", monthType='" + getMonthType() + "'" +
            ", closeYear=" + getCloseYear() +
            ", flag='" + getFlag() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", financialAccountYear=" + getFinancialAccountYearId() +
            ", financialAccountYear='" + getFinancialAccountYearDurationStr() + "'" +
            "}";
    }
}
