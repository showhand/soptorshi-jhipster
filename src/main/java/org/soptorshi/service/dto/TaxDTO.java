package org.soptorshi.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.TaxStatus;

/**
 * A DTO for the Tax entity.
 */
public class TaxDTO implements Serializable {

    private Long id;

    private BigDecimal minimumSalary;

    private Double rate;

    private TaxStatus taxStatus;


    private Long financialAccountYearId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(BigDecimal minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public TaxStatus getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(TaxStatus taxStatus) {
        this.taxStatus = taxStatus;
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

        TaxDTO taxDTO = (TaxDTO) o;
        if (taxDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taxDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaxDTO{" +
            "id=" + getId() +
            ", minimumSalary=" + getMinimumSalary() +
            ", rate=" + getRate() +
            ", taxStatus='" + getTaxStatus() + "'" +
            ", financialAccountYear=" + getFinancialAccountYearId() +
            "}";
    }
}
