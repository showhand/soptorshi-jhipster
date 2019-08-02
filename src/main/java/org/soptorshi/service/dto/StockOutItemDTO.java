package org.soptorshi.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StockOutItem entity.
 */
public class StockOutItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String containerTrackingId;

    @NotNull
    private Double quantity;

    private String stockOutBy;

    private Instant stockOutDate;

    private String receiverId;

    private String remarks;


    private Long itemCategoriesId;

    private String itemCategoriesName;

    private Long itemSubCategoriesId;

    private String itemSubCategoriesName;

    private Long inventoryLocationsId;

    private String inventoryLocationsName;

    private Long inventorySubLocationsId;

    private String inventorySubLocationsName;

    private Long stockInItemsId;

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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getStockOutBy() {
        return stockOutBy;
    }

    public void setStockOutBy(String stockOutBy) {
        this.stockOutBy = stockOutBy;
    }

    public Instant getStockOutDate() {
        return stockOutDate;
    }

    public void setStockOutDate(Instant stockOutDate) {
        this.stockOutDate = stockOutDate;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Long getStockInItemsId() {
        return stockInItemsId;
    }

    public void setStockInItemsId(Long stockInItemId) {
        this.stockInItemsId = stockInItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockOutItemDTO stockOutItemDTO = (StockOutItemDTO) o;
        if (stockOutItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockOutItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockOutItemDTO{" +
            "id=" + getId() +
            ", containerTrackingId='" + getContainerTrackingId() + "'" +
            ", quantity=" + getQuantity() +
            ", stockOutBy='" + getStockOutBy() + "'" +
            ", stockOutDate='" + getStockOutDate() + "'" +
            ", receiverId='" + getReceiverId() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", itemCategories=" + getItemCategoriesId() +
            ", itemCategories='" + getItemCategoriesName() + "'" +
            ", itemSubCategories=" + getItemSubCategoriesId() +
            ", itemSubCategories='" + getItemSubCategoriesName() + "'" +
            ", inventoryLocations=" + getInventoryLocationsId() +
            ", inventoryLocations='" + getInventoryLocationsName() + "'" +
            ", inventorySubLocations=" + getInventorySubLocationsId() +
            ", inventorySubLocations='" + getInventorySubLocationsName() + "'" +
            ", stockInItems=" + getStockInItemsId() +
            "}";
    }
}
