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


    private Long productCategoriesId;

    private String productCategoriesName;

    private Long productsId;

    private String productsName;

    private Long inventoryLocationsId;

    private String inventoryLocationsName;

    private Long inventorySubLocationsId;

    private String inventorySubLocationsName;

    private Long stockInItemsId;

    private Long stockStatusesId;

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

    public Long getProductCategoriesId() {
        return productCategoriesId;
    }

    public void setProductCategoriesId(Long productCategoryId) {
        this.productCategoriesId = productCategoryId;
    }

    public String getProductCategoriesName() {
        return productCategoriesName;
    }

    public void setProductCategoriesName(String productCategoryName) {
        this.productCategoriesName = productCategoryName;
    }

    public Long getProductsId() {
        return productsId;
    }

    public void setProductsId(Long productId) {
        this.productsId = productId;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productName) {
        this.productsName = productName;
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

    public Long getStockStatusesId() {
        return stockStatusesId;
    }

    public void setStockStatusesId(Long stockStatusId) {
        this.stockStatusesId = stockStatusId;
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
            ", productCategories=" + getProductCategoriesId() +
            ", productCategories='" + getProductCategoriesName() + "'" +
            ", products=" + getProductsId() +
            ", products='" + getProductsName() + "'" +
            ", inventoryLocations=" + getInventoryLocationsId() +
            ", inventoryLocations='" + getInventoryLocationsName() + "'" +
            ", inventorySubLocations=" + getInventorySubLocationsId() +
            ", inventorySubLocations='" + getInventorySubLocationsName() + "'" +
            ", stockInItems=" + getStockInItemsId() +
            ", stockStatuses=" + getStockStatusesId() +
            "}";
    }
}
