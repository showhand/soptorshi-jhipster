package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.AllowanceType;
import org.soptorshi.domain.enumeration.AllowanceCategory;

/**
 * A DTO for the DesignationWiseAllowance entity.
 */
public class DesignationWiseAllowanceDTO implements Serializable {

    private Long id;

    private AllowanceType allowanceType;

    private AllowanceCategory allowanceCategory;

    private BigDecimal amount;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long designationId;

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

    public AllowanceCategory getAllowanceCategory() {
        return allowanceCategory;
    }

    public void setAllowanceCategory(AllowanceCategory allowanceCategory) {
        this.allowanceCategory = allowanceCategory;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DesignationWiseAllowanceDTO designationWiseAllowanceDTO = (DesignationWiseAllowanceDTO) o;
        if (designationWiseAllowanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designationWiseAllowanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DesignationWiseAllowanceDTO{" +
            "id=" + getId() +
            ", allowanceType='" + getAllowanceType() + "'" +
            ", allowanceCategory='" + getAllowanceCategory() + "'" +
            ", amount=" + getAmount() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", designation=" + getDesignationId() +
            "}";
    }
}
