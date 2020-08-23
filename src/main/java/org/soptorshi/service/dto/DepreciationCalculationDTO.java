package org.soptorshi.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.MonthType;

/**
 * A DTO for the DepreciationCalculation entity.
 */
public class DepreciationCalculationDTO implements Serializable {

    private Long id;

    private MonthType monthType;

    private Boolean isExecuted;

    private String createdBy;

    private Instant createdOn;

    private String modifiedBy;

    private Instant modifiedOn;


    private Long financialAccountYearId;

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

    public Boolean isIsExecuted() {
        return isExecuted;
    }

    public void setIsExecuted(Boolean isExecuted) {
        this.isExecuted = isExecuted;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getFinancialAccountYearId() {
        return financialAccountYearId;
    }

    public void setFinancialAccountYearId(Long financialAccountYearId) {
        this.financialAccountYearId = financialAccountYearId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepreciationCalculationDTO depreciationCalculationDTO = (DepreciationCalculationDTO) o;
        if (depreciationCalculationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depreciationCalculationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepreciationCalculationDTO{" +
            "id=" + getId() +
            ", monthType='" + getMonthType() + "'" +
            ", isExecuted='" + isIsExecuted() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", financialAccountYear=" + getFinancialAccountYearId() +
            "}";
    }
}
