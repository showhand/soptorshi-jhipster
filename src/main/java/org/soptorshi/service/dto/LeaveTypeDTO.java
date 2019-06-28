package org.soptorshi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.PaidOrUnPaid;

/**
 * A DTO for the LeaveType entity.
 */
public class LeaveTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    private PaidOrUnPaid paidLeave;

    private Integer maximumNumberOfDays;

    @Size(max = 250)
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaidOrUnPaid getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(PaidOrUnPaid paidLeave) {
        this.paidLeave = paidLeave;
    }

    public Integer getMaximumNumberOfDays() {
        return maximumNumberOfDays;
    }

    public void setMaximumNumberOfDays(Integer maximumNumberOfDays) {
        this.maximumNumberOfDays = maximumNumberOfDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeaveTypeDTO leaveTypeDTO = (LeaveTypeDTO) o;
        if (leaveTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaveTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaveTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", paidLeave='" + getPaidLeave() + "'" +
            ", maximumNumberOfDays=" + getMaximumNumberOfDays() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
