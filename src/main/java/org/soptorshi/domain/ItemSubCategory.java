package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ItemSubCategory.
 */
@Entity
@Table(name = "item_sub_category")
@Document(indexName = "itemsubcategory")
public class ItemSubCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("itemSubCategories")
    private ItemCategory itemCategories;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ItemSubCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public ItemSubCategory shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public ItemSubCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemCategory getItemCategories() {
        return itemCategories;
    }

    public ItemSubCategory itemCategories(ItemCategory itemCategory) {
        this.itemCategories = itemCategory;
        return this;
    }

    public void setItemCategories(ItemCategory itemCategory) {
        this.itemCategories = itemCategory;
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
        ItemSubCategory itemSubCategory = (ItemSubCategory) o;
        if (itemSubCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemSubCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemSubCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
