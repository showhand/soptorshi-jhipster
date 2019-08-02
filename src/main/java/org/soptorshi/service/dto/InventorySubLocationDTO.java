package org.soptorshi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.InventorySubLocationCategory;

/**
 * A DTO for the InventorySubLocation entity.
 */
public class InventorySubLocationDTO implements Serializable {

    private Long id;

    @NotNull
    private InventorySubLocationCategory category;

    @NotNull
    private String name;

    private String shortName;

    private String description;


    private Long inventoryLocationId;

    private String inventoryLocationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventorySubLocationCategory getCategory() {
        return category;
    }

    public void setCategory(InventorySubLocationCategory category) {
        this.category = category;
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

    public Long getInventoryLocationId() {
        return inventoryLocationId;
    }

    public void setInventoryLocationId(Long inventoryLocationId) {
        this.inventoryLocationId = inventoryLocationId;
    }

    public String getInventoryLocationName() {
        return inventoryLocationName;
    }

    public void setInventoryLocationName(String inventoryLocationName) {
        this.inventoryLocationName = inventoryLocationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InventorySubLocationDTO inventorySubLocationDTO = (InventorySubLocationDTO) o;
        if (inventorySubLocationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventorySubLocationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventorySubLocationDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            ", inventoryLocation=" + getInventoryLocationId() +
            ", inventoryLocation='" + getInventoryLocationName() + "'" +
            "}";
    }
}
