package org.soptorshi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ItemSubCategory entity.
 */
public class ItemSubCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String shortName;

    private String description;


    private Long itemCategoriesId;

    private String itemCategoriesName;

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

    public Long getItemCategoriesId() {
        return itemCategoriesId;
    }

    public void setItemCategoriesId(Long itemCategoryId) {
        this.itemCategoriesId = itemCategoryId;
    }

    public String getItemCategoriesName() {
        return itemCategoriesName;
    }

    public void setItemCategoriesName(String itemCategoryName) {
        this.itemCategoriesName = itemCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemSubCategoryDTO itemSubCategoryDTO = (ItemSubCategoryDTO) o;
        if (itemSubCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemSubCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemSubCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            ", itemCategories=" + getItemCategoriesId() +
            ", itemCategories='" + getItemCategoriesName() + "'" +
            "}";
    }
}
