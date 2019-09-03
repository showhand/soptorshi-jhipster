package org.soptorshi.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.ItemUnit;

/**
 * A DTO for the StockStatus entity.
 */
public class StockStatusDTO implements Serializable {

    private Long id;

    @NotNull
    private String containerTrackingId;

    @NotNull
    private Double totalQuantity;

    @NotNull
    private ItemUnit unit;

    @NotNull
    private Double availableQuantity;

    @NotNull
    private Double totalPrice;

    @NotNull
    private Double availablePrice;

    private String stockInBy;

    private Instant stockInDate;


    private Long stockInItemsId;

    private Long itemCategoriesId;

    private String itemCategoriesName;

    private Long itemSubCategoriesId;

    private String itemSubCategoriesName;

    private Long inventoryLocationsId;

    private String inventoryLocationsName;

    private Long inventorySubLocationsId;

    private String inventorySubLocationsName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContainerTrackingId() {
        return containerTrackingId;
    }

    public void setContainerTrackingId(String containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public ItemUnit getUnit() {
        return unit;
    }

    public void setUnit(ItemUnit unit) {
        this.unit = unit;
    }

    public Double getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Double availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getAvailablePrice() {
        return availablePrice;
    }

    public void setAvailablePrice(Double availablePrice) {
        this.availablePrice = availablePrice;
    }

    public String getStockInBy() {
        return stockInBy;
    }

    public void setStockInBy(String stockInBy) {
        this.stockInBy = stockInBy;
    }

    public Instant getStockInDate() {
        return stockInDate;
    }

    public void setStockInDate(Instant stockInDate) {
        this.stockInDate = stockInDate;
    }

    public Long getStockInItemsId() {
        return stockInItemsId;
    }

    public void setStockInItemsId(Long stockInItemId) {
        this.stockInItemsId = stockInItemId;
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

    public Long getItemSubCategoriesId() {
        return itemSubCategoriesId;
    }

    public void setItemSubCategoriesId(Long itemSubCategoryId) {
        this.itemSubCategoriesId = itemSubCategoryId;
    }

    public String getItemSubCategoriesName() {
        return itemSubCategoriesName;
    }

    public void setItemSubCategoriesName(String itemSubCategoryName) {
        this.itemSubCategoriesName = itemSubCategoryName;
    }

    public Long getInventoryLocationsId() {
        return inventoryLocationsId;
    }

    public void setInventoryLocationsId(Long inventoryLocationId) {
        this.inventoryLocationsId = inventoryLocationId;
    }

    public String getInventoryLocationsName() {
        return inventoryLocationsName;
    }

    public void setInventoryLocationsName(String inventoryLocationName) {
        this.inventoryLocationsName = inventoryLocationName;
    }

    public Long getInventorySubLocationsId() {
        return inventorySubLocationsId;
    }

    public void setInventorySubLocationsId(Long inventorySubLocationId) {
        this.inventorySubLocationsId = inventorySubLocationId;
    }

    public String getInventorySubLocationsName() {
        return inventorySubLocationsName;
    }

    public void setInventorySubLocationsName(String inventorySubLocationName) {
        this.inventorySubLocationsName = inventorySubLocationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockStatusDTO stockStatusDTO = (StockStatusDTO) o;
        if (stockStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockStatusDTO{" +
            "id=" + getId() +
            ", containerTrackingId='" + getContainerTrackingId() + "'" +
            ", totalQuantity=" + getTotalQuantity() +
            ", unit='" + getUnit() + "'" +
            ", availableQuantity=" + getAvailableQuantity() +
            ", totalPrice=" + getTotalPrice() +
            ", availablePrice=" + getAvailablePrice() +
            ", stockInBy='" + getStockInBy() + "'" +
            ", stockInDate='" + getStockInDate() + "'" +
            ", stockInItems=" + getStockInItemsId() +
            ", itemCategories=" + getItemCategoriesId() +
            ", itemCategories='" + getItemCategoriesName() + "'" +
            ", itemSubCategories=" + getItemSubCategoriesId() +
            ", itemSubCategories='" + getItemSubCategoriesName() + "'" +
            ", inventoryLocations=" + getInventoryLocationsId() +
            ", inventoryLocations='" + getInventoryLocationsName() + "'" +
            ", inventorySubLocations=" + getInventorySubLocationsId() +
            ", inventorySubLocations='" + getInventorySubLocationsName() + "'" +
            "}";
    }
}