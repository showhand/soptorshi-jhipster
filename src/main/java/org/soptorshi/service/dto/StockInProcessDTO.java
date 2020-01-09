package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.ContainerCategory;
import org.soptorshi.domain.enumeration.ProductType;
import org.soptorshi.domain.enumeration.StockInProcessStatus;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the StockInProcess entity.
 */
public class StockInProcessDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal totalQuantity;

    @NotNull
    private UnitOfMeasurements unit;

    @NotNull
    private BigDecimal unitPrice;

    private Integer totalContainer;

    private ContainerCategory containerCategory;

    private String containerTrackingId;

    private String quantityPerContainer;

    private LocalDate mfgDate;

    private LocalDate expiryDate;

    private ProductType typeOfProduct;

    private StockInProcessStatus status;

    private String processStartedBy;

    private Instant processStartedOn;

    private String stockInBy;

    private Instant stockInDate;

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

    private Long requisitionsId;

    private String requisitionsRequisitionNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
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

    public LocalDate getMfgDate() {
        return mfgDate;
    }

    public void setMfgDate(LocalDate mfgDate) {
        this.mfgDate = mfgDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ProductType getTypeOfProduct() {
        return typeOfProduct;
    }

    public void setTypeOfProduct(ProductType typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }

    public StockInProcessStatus getStatus() {
        return status;
    }

    public void setStatus(StockInProcessStatus status) {
        this.status = status;
    }

    public String getProcessStartedBy() {
        return processStartedBy;
    }

    public void setProcessStartedBy(String processStartedBy) {
        this.processStartedBy = processStartedBy;
    }

    public Instant getProcessStartedOn() {
        return processStartedOn;
    }

    public void setProcessStartedOn(Instant processStartedOn) {
        this.processStartedOn = processStartedOn;
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

    public Long getRequisitionsId() {
        return requisitionsId;
    }

    public void setRequisitionsId(Long requisitionId) {
        this.requisitionsId = requisitionId;
    }

    public String getRequisitionsRequisitionNo() {
        return requisitionsRequisitionNo;
    }

    public void setRequisitionsRequisitionNo(String requisitionRequisitionNo) {
        this.requisitionsRequisitionNo = requisitionRequisitionNo;
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
            ", mfgDate='" + getMfgDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", typeOfProduct='" + getTypeOfProduct() + "'" +
            ", status='" + getStatus() + "'" +
            ", processStartedBy='" + getProcessStartedBy() + "'" +
            ", processStartedOn='" + getProcessStartedOn() + "'" +
            ", stockInBy='" + getStockInBy() + "'" +
            ", stockInDate='" + getStockInDate() + "'" +
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
            ", requisitions=" + getRequisitionsId() +
            ", requisitions='" + getRequisitionsRequisitionNo() + "'" +
            "}";
    }
}
