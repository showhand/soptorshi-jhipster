package org.soptorshi.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.ItemUnit;
import org.soptorshi.domain.enumeration.ContainerCategory;

/**
 * A DTO for the StockInProcess entity.
 */
public class StockInProcessDTO implements Serializable {

    private Long id;

    @NotNull
    private Double totalQuantity;

    @NotNull
    private ItemUnit unit;

    @NotNull
    private Double unitPrice;

    @NotNull
    private Integer totalContainer;

    @NotNull
    private ContainerCategory containerCategory;

    @NotNull
    private String containerTrackingId;

    @NotNull
    private String quantityPerContainer;

    private LocalDate expiryDate;

    private String stockInBy;

    private Instant stockInDate;

    private String purchaseOrderId;

    private String remarks;


    private Long itemCategoriesId;

    private String itemCategoriesName;

    private Long itemSubCategoriesId;

    private String itemSubCategoriesName;

    private Long inventoryLocationsId;

    private String inventoryLocationsName;

    private Long inventorySubLocationsId;

    private String inventorySubLocationsName;

    private Long manufacturersId;

    private String manufacturersName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getTotalContainer() {
        return totalContainer;
    }

    public void setTotalContainer(Integer totalContainer) {
        this.totalContainer = totalContainer;
    }

    public ContainerCategory getContainerCategory() {
        return containerCategory;
    }

    public void setContainerCategory(ContainerCategory containerCategory) {
        this.containerCategory = containerCategory;
    }

    public String getContainerTrackingId() {
        return containerTrackingId;
    }

    public void setContainerTrackingId(String containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
    }

    public String getQuantityPerContainer() {
        return quantityPerContainer;
    }

    public void setQuantityPerContainer(String quantityPerContainer) {
        this.quantityPerContainer = quantityPerContainer;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
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

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
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

    public Long getManufacturersId() {
        return manufacturersId;
    }

    public void setManufacturersId(Long manufacturerId) {
        this.manufacturersId = manufacturerId;
    }

    public String getManufacturersName() {
        return manufacturersName;
    }

    public void setManufacturersName(String manufacturerName) {
        this.manufacturersName = manufacturerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockInProcessDTO stockInProcessDTO = (StockInProcessDTO) o;
        if (stockInProcessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockInProcessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockInProcessDTO{" +
            "id=" + getId() +
            ", totalQuantity=" + getTotalQuantity() +
            ", unit='" + getUnit() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", totalContainer=" + getTotalContainer() +
            ", containerCategory='" + getContainerCategory() + "'" +
            ", containerTrackingId='" + getContainerTrackingId() + "'" +
            ", quantityPerContainer='" + getQuantityPerContainer() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", stockInBy='" + getStockInBy() + "'" +
            ", stockInDate='" + getStockInDate() + "'" +
            ", purchaseOrderId='" + getPurchaseOrderId() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", itemCategories=" + getItemCategoriesId() +
            ", itemCategories='" + getItemCategoriesName() + "'" +
            ", itemSubCategories=" + getItemSubCategoriesId() +
            ", itemSubCategories='" + getItemSubCategoriesName() + "'" +
            ", inventoryLocations=" + getInventoryLocationsId() +
            ", inventoryLocations='" + getInventoryLocationsName() + "'" +
            ", inventorySubLocations=" + getInventorySubLocationsId() +
            ", inventorySubLocations='" + getInventorySubLocationsName() + "'" +
            ", manufacturers=" + getManufacturersId() +
            ", manufacturers='" + getManufacturersName() + "'" +
            "}";
    }
}
