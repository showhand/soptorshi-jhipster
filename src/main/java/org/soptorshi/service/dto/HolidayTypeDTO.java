package org.soptorshi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.YesOrNo;

/**
 * A DTO for the HolidayType entity.
 */
public class HolidayTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private YesOrNo moonDependency;


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

    public YesOrNo getMoonDependency() {
        return moonDependency;
    }

    public void setMoonDependency(YesOrNo moonDependency) {
        this.moonDependency = moonDependency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HolidayTypeDTO holidayTypeDTO = (HolidayTypeDTO) o;
        if (holidayTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), holidayTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HolidayTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", moonDependency='" + getMoonDependency() + "'" +
            "}";
    }
}
