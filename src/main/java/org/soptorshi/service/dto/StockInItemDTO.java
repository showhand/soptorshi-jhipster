package org.soptorshi.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import org.soptorshi.domain.enumeration.ContainerCategory;

/**
 * A DTO for the StockInItem entity.
 */
public class StockInItemDTO implements Serializable {

    private Long id;

    @NotNull
    private Double quantity;

    @NotNull
    private UnitOfMeasurements unit;

    @NotNull
    private Double price;

    @NotNull
    private ContainerCategory containerCategory;

    @NotNull
    private String containerTrackingId;

    private LocalDate expiryDate;

    private String stockInBy;

    private Instant stockInDate;

    private String purchaseOrderId;

    private String remarks;


    private Long productCategoriesId;

    private String productCategoriesName;

    private Long productsId;

    private String productsName;

    private Long inventoryLocationsId;

    private String inventoryLocationsName;

    private Long inventorySubLocationsId;

    private String inventorySubLocationsName;

    private Long vendorId;

    private String vendorCompanyName;

    private Long stockInProcessesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public UnitOfMeasurements getUnit() {
        return unit;
    }

    public void setUnit(UnitOfMeasurements unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorCompanyName() {
        return vendorCompanyName;
    }

    public void setVendorCompanyName(String vendorCompanyName) {
        this.vendorCompanyName = vendorCompanyName;
    }

    public Long getStockInProcessesId() {
        return stockInProcessesId;
    }

    public void setStockInProcessesId(Long stockInProcessId) {
        this.stockInProcessesId = stockInProcessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockInItemDTO stockInItemDTO = (StockInItemDTO) o;
        if (stockInItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockInItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockInItemDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unit='" + getUnit() + "'" +
            ", price=" + getPrice() +
            ", containerCategory='" + getContainerCategory() + "'" +
            ", containerTrackingId='" + getContainerTrackingId() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", stockInBy='" + getStockInBy() + "'" +
            ", stockInDate='" + getStockInDate() + "'" +
            ", purchaseOrderId='" + getPurchaseOrderId() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", productCategories=" + getProductCategoriesId() +
            ", productCategories='" + getProductCategoriesName() + "'" +
            ", products=" + getProductsId() +
            ", products='" + getProductsName() + "'" +
            ", inventoryLocations=" + getInventoryLocationsId() +
            ", inventoryLocations='" + getInventoryLocationsName() + "'" +
            ", inventorySubLocations=" + getInventorySubLocationsId() +
            ", inventorySubLocations='" + getInventorySubLocationsName() + "'" +
            ", vendor=" + getVendorId() +
            ", vendor='" + getVendorCompanyName() + "'" +
            ", stockInProcesses=" + getStockInProcessesId() +
            "}";
    }
}
