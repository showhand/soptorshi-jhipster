package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import org.soptorshi.domain.enumeration.RequisitionStatus;

/**
 * A DTO for the Requisition entity.
 */
public class RequisitionDTO implements Serializable {

    private Long id;

    private String requisitionNo;

    @Lob
    private String reason;

    private LocalDate requisitionDate;

    private BigDecimal amount;

    private RequisitionStatus status;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long employeeId;

    private String employeeFullName;

    private Long officeId;

    private String officeName;

    private Long departmentId;

    private String departmentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequisitionNo() {
        return requisitionNo;
    }

    public void setRequisitionNo(String requisitionNo) {
        this.requisitionNo = requisitionNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(LocalDate requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public RequisitionStatus getStatus() {
        return status;
    }

    public void setStatus(RequisitionStatus status) {
        this.status = status;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequisitionDTO requisitionDTO = (RequisitionDTO) o;
        if (requisitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requisitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequisitionDTO{" +
            "id=" + getId() +
            ", requisitionNo='" + getRequisitionNo() + "'" +
            ", reason='" + getReason() + "'" +
            ", requisitionDate='" + getRequisitionDate() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeFullName() + "'" +
            ", office=" + getOfficeId() +
            ", office='" + getOfficeName() + "'" +
            ", department=" + getDepartmentId() +
            ", department='" + getDepartmentName() + "'" +
            "}";
    }
}
