package org.soptorshi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ItemCategory entity.
 */
public class ItemCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String shortName;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

        ItemCategoryDTO itemCategoryDTO = (ItemCategoryDTO) o;
        if (itemCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
