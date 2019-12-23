package org.soptorshi.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;

/**
 * A DTO for the StockStatus entity.
 */
public class StockStatusDTO implements Serializable {

    private Long id;

    @NotNull
    private String containerTrackingId;

    @NotNull
    private BigDecimal totalQuantity;

    @NotNull
    private UnitOfMeasurements unit;

    @NotNull
    private BigDecimal availableQuantity;

    @NotNull
    private BigDecimal totalPrice;

    @NotNull
    private BigDecimal availablePrice;

    private String stockInBy;

    private Instant stockInDate;


    private Long stockInItemId;

    private Long productCategoriesId;

    private String productCategoriesName;

    private Long productsId;

    private String productsName;

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

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public UnitOfMeasurements getUnit() {
        return unit;
    }

    public void setUnit(UnitOfMeasurements unit) {
        this.unit = unit;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getAvailablePrice() {
        return availablePrice;
    }

    public void setAvailablePrice(BigDecimal availablePrice) {
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

    public Long getStockInItemId() {
        return stockInItemId;
    }

    public void setStockInItemId(Long stockInItemId) {
        this.stockInItemId = stockInItemId;
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
            ", stockInItem=" + getStockInItemId() +
            ", productCategories=" + getProductCategoriesId() +
            ", productCategories='" + getProductCategoriesName() + "'" +
            ", products=" + getProductsId() +
            ", products='" + getProductsName() + "'" +
            ", inventoryLocations=" + getInventoryLocationsId() +
            ", inventoryLocations='" + getInventoryLocationsName() + "'" +
            ", inventorySubLocations=" + getInventorySubLocationsId() +
            ", inventorySubLocations='" + getInventorySubLocationsName() + "'" +
            "}";
    }
}
