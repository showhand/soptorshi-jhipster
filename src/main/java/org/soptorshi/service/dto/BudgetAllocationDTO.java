package org.soptorshi.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the BudgetAllocation entity.
 */
public class BudgetAllocationDTO implements Serializable {

    private Long id;

    private BigDecimal amount;


    private Long officeId;

    private String officeName;

    private Long departmentId;

    private String departmentName;

    private Long financialAccountYearId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

        BudgetAllocationDTO budgetAllocationDTO = (BudgetAllocationDTO) o;
        if (budgetAllocationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), budgetAllocationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BudgetAllocationDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", office=" + getOfficeId() +
            ", office='" + getOfficeName() + "'" +
            ", department=" + getDepartmentId() +
            ", department='" + getDepartmentName() + "'" +
            ", financialAccountYear=" + getFinancialAccountYearId() +
            "}";
    }
}
