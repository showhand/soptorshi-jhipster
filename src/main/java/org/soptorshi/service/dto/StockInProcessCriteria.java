package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.ContainerCategory;
import org.soptorshi.domain.enumeration.ProductType;
import org.soptorshi.domain.enumeration.StockInProcessStatus;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the StockInProcess entity. This class is used in StockInProcessResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /stock-in-processes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockInProcessCriteria implements Serializable {
    /**
     * Class for filtering UnitOfMeasurements
     */
    public static class UnitOfMeasurementsFilter extends Filter<UnitOfMeasurements> {
    }
    /**
     * Class for filtering ContainerCategory
     */
    public static class ContainerCategoryFilter extends Filter<ContainerCategory> {
    }
    /**
     * Class for filtering ProductType
     */
    public static class ProductTypeFilter extends Filter<ProductType> {
    }
    /**
     * Class for filtering StockInProcessStatus
     */
    public static class StockInProcessStatusFilter extends Filter<StockInProcessStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter totalQuantity;

    private UnitOfMeasurementsFilter unit;

    private BigDecimalFilter unitPrice;

    private IntegerFilter totalContainer;

    private ContainerCategoryFilter containerCategory;

    private StringFilter containerTrackingId;

    private StringFilter quantityPerContainer;

    private LocalDateFilter mfgDate;

    private LocalDateFilter expiryDate;

    private ProductTypeFilter typeOfProduct;

    private StockInProcessStatusFilter status;

    private StringFilter processStartedBy;

    private InstantFilter processStartedOn;

    private StringFilter stockInBy;

    private InstantFilter stockInDate;

    private StringFilter remarks;

    private LongFilter productCategoriesId;

    private LongFilter productsId;

    private LongFilter inventoryLocationsId;

    private LongFilter inventorySubLocationsId;

    private LongFilter vendorId;

    private LongFilter requisitionsId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimalFilter totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public UnitOfMeasurementsFilter getUnit() {
        return unit;
    }

    public void setUnit(UnitOfMeasurementsFilter unit) {
        this.unit = unit;
    }

    public BigDecimalFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimalFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public IntegerFilter getTotalContainer() {
        return totalContainer;
    }

    public void setTotalContainer(IntegerFilter totalContainer) {
        this.totalContainer = totalContainer;
    }

    public ContainerCategoryFilter getContainerCategory() {
        return containerCategory;
    }

    public void setContainerCategory(ContainerCategoryFilter containerCategory) {
        this.containerCategory = containerCategory;
    }

    public StringFilter getContainerTrackingId() {
        return containerTrackingId;
    }

    public void setContainerTrackingId(StringFilter containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
    }

    public StringFilter getQuantityPerContainer() {
        return quantityPerContainer;
    }

    public void setQuantityPerContainer(StringFilter quantityPerContainer) {
        this.quantityPerContainer = quantityPerContainer;
    }

    public LocalDateFilter getMfgDate() {
        return mfgDate;
    }

    public void setMfgDate(LocalDateFilter mfgDate) {
        this.mfgDate = mfgDate;
    }

    public LocalDateFilter getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ProductTypeFilter getTypeOfProduct() {
        return typeOfProduct;
    }

    public void setTypeOfProduct(ProductTypeFilter typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }

    public StockInProcessStatusFilter getStatus() {
        return status;
    }

    public void setStatus(StockInProcessStatusFilter status) {
        this.status = status;
    }

    public StringFilter getProcessStartedBy() {
        return processStartedBy;
    }

    public void setProcessStartedBy(StringFilter processStartedBy) {
        this.processStartedBy = processStartedBy;
    }

    public InstantFilter getProcessStartedOn() {
        return processStartedOn;
    }

    public void setProcessStartedOn(InstantFilter processStartedOn) {
        this.processStartedOn = processStartedOn;
    }

    public StringFilter getStockInBy() {
        return stockInBy;
    }

    public void setStockInBy(StringFilter stockInBy) {
        this.stockInBy = stockInBy;
    }

    public InstantFilter getStockInDate() {
        return stockInDate;
    }

    public void setStockInDate(InstantFilter stockInDate) {
        this.stockInDate = stockInDate;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getProductCategoriesId() {
        return productCategoriesId;
    }

    public void setProductCategoriesId(LongFilter productCategoriesId) {
        this.productCategoriesId = productCategoriesId;
    }

    public LongFilter getProductsId() {
        return productsId;
    }

    public void setProductsId(LongFilter productsId) {
        this.productsId = productsId;
    }

    public LongFilter getInventoryLocationsId() {
        return inventoryLocationsId;
    }

    public void setInventoryLocationsId(LongFilter inventoryLocationsId) {
        this.inventoryLocationsId = inventoryLocationsId;
    }

    public LongFilter getInventorySubLocationsId() {
        return inventorySubLocationsId;
    }

    public void setInventorySubLocationsId(LongFilter inventorySubLocationsId) {
        this.inventorySubLocationsId = inventorySubLocationsId;
    }

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public LongFilter getRequisitionsId() {
        return requisitionsId;
    }

    public void setRequisitionsId(LongFilter requisitionsId) {
        this.requisitionsId = requisitionsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockInProcessCriteria that = (StockInProcessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalQuantity, that.totalQuantity) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(totalContainer, that.totalContainer) &&
            Objects.equals(containerCategory, that.containerCategory) &&
            Objects.equals(containerTrackingId, that.containerTrackingId) &&
            Objects.equals(quantityPerContainer, that.quantityPerContainer) &&
            Objects.equals(mfgDate, that.mfgDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(typeOfProduct, that.typeOfProduct) &&
            Objects.equals(status, that.status) &&
            Objects.equals(processStartedBy, that.processStartedBy) &&
            Objects.equals(processStartedOn, that.processStartedOn) &&
            Objects.equals(stockInBy, that.stockInBy) &&
            Objects.equals(stockInDate, that.stockInDate) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(productCategoriesId, that.productCategoriesId) &&
            Objects.equals(productsId, that.productsId) &&
            Objects.equals(inventoryLocationsId, that.inventoryLocationsId) &&
            Objects.equals(inventorySubLocationsId, that.inventorySubLocationsId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(requisitionsId, that.requisitionsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalQuantity,
        unit,
        unitPrice,
        totalContainer,
        containerCategory,
        containerTrackingId,
        quantityPerContainer,
        mfgDate,
        expiryDate,
        typeOfProduct,
        status,
        processStartedBy,
        processStartedOn,
        stockInBy,
        stockInDate,
        remarks,
        productCategoriesId,
        productsId,
        inventoryLocationsId,
        inventorySubLocationsId,
        vendorId,
        requisitionsId
        );
    }

    @Override
    public String toString() {
        return "StockInProcessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalQuantity != null ? "totalQuantity=" + totalQuantity + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (totalContainer != null ? "totalContainer=" + totalContainer + ", " : "") +
                (containerCategory != null ? "containerCategory=" + containerCategory + ", " : "") +
                (containerTrackingId != null ? "containerTrackingId=" + containerTrackingId + ", " : "") +
                (quantityPerContainer != null ? "quantityPerContainer=" + quantityPerContainer + ", " : "") +
                (mfgDate != null ? "mfgDate=" + mfgDate + ", " : "") +
                (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
                (typeOfProduct != null ? "typeOfProduct=" + typeOfProduct + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (processStartedBy != null ? "processStartedBy=" + processStartedBy + ", " : "") +
                (processStartedOn != null ? "processStartedOn=" + processStartedOn + ", " : "") +
                (stockInBy != null ? "stockInBy=" + stockInBy + ", " : "") +
                (stockInDate != null ? "stockInDate=" + stockInDate + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (productCategoriesId != null ? "productCategoriesId=" + productCategoriesId + ", " : "") +
                (productsId != null ? "productsId=" + productsId + ", " : "") +
                (inventoryLocationsId != null ? "inventoryLocationsId=" + inventoryLocationsId + ", " : "") +
                (inventorySubLocationsId != null ? "inventorySubLocationsId=" + inventorySubLocationsId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (requisitionsId != null ? "requisitionsId=" + requisitionsId + ", " : "") +
            "}";
    }

}
