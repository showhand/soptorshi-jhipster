package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.InventorySubLocationCategory;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the InventorySubLocation entity. This class is used in InventorySubLocationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /inventory-sub-locations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventorySubLocationCriteria implements Serializable {
    /**
     * Class for filtering InventorySubLocationCategory
     */
    public static class InventorySubLocationCategoryFilter extends Filter<InventorySubLocationCategory> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InventorySubLocationCategoryFilter category;

    private StringFilter name;

    private StringFilter shortName;

    private StringFilter description;

    private LongFilter inventoryLocationsId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InventorySubLocationCategoryFilter getCategory() {
        return category;
    }

    public void setCategory(InventorySubLocationCategoryFilter category) {
        this.category = category;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getShortName() {
        return shortName;
    }

    public void setShortName(StringFilter shortName) {
        this.shortName = shortName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getInventoryLocationsId() {
        return inventoryLocationsId;
    }

    public void setInventoryLocationsId(LongFilter inventoryLocationsId) {
        this.inventoryLocationsId = inventoryLocationsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InventorySubLocationCriteria that = (InventorySubLocationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(category, that.category) &&
            Objects.equals(name, that.name) &&
            Objects.equals(shortName, that.shortName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(inventoryLocationsId, that.inventoryLocationsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        category,
        name,
        shortName,
        description,
        inventoryLocationsId
        );
    }

    @Override
    public String toString() {
        return "InventorySubLocationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (category != null ? "category=" + category + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (shortName != null ? "shortName=" + shortName + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (inventoryLocationsId != null ? "inventoryLocationsId=" + inventoryLocationsId + ", " : "") +
            "}";
    }

}
