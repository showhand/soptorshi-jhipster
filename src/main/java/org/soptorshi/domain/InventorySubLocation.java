package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import org.soptorshi.domain.enumeration.InventorySubLocationCategory;

/**
 * A InventorySubLocation.
 */
@Entity
@Table(name = "inventory_sub_location")
@Document(indexName = "inventorysublocation")
public class InventorySubLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private InventorySubLocationCategory category;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("inventorySubLocations")
    private InventoryLocation inventoryLocations;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventorySubLocationCategory getCategory() {
        return category;
    }

    public InventorySubLocation category(InventorySubLocationCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(InventorySubLocationCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public InventorySubLocation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public InventorySubLocation shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public InventorySubLocation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InventoryLocation getInventoryLocations() {
        return inventoryLocations;
    }

    public InventorySubLocation inventoryLocations(InventoryLocation inventoryLocation) {
        this.inventoryLocations = inventoryLocation;
        return this;
    }

    public void setInventoryLocations(InventoryLocation inventoryLocation) {
        this.inventoryLocations = inventoryLocation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InventorySubLocation inventorySubLocation = (InventorySubLocation) o;
        if (inventorySubLocation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventorySubLocation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventorySubLocation{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
