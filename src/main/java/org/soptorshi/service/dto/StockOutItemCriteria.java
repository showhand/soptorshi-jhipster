package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the StockOutItem entity. This class is used in StockOutItemResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /stock-out-items?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockOutItemCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter containerTrackingId;

    private BigDecimalFilter quantity;

    private StringFilter stockOutBy;

    private InstantFilter stockOutDate;

    private StringFilter receiverId;

    private StringFilter remarks;

    private LongFilter productCategoriesId;

    private LongFilter productsId;

    private LongFilter inventoryLocationsId;

    private LongFilter inventorySubLocationsId;

    private LongFilter stockInItemsId;

    private LongFilter stockStatusesId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getContainerTrackingId() {
        return containerTrackingId;
    }

    public void setContainerTrackingId(StringFilter containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getStockOutBy() {
        return stockOutBy;
    }

    public void setStockOutBy(StringFilter stockOutBy) {
        this.stockOutBy = stockOutBy;
    }

    public InstantFilter getStockOutDate() {
        return stockOutDate;
    }

    public void setStockOutDate(InstantFilter stockOutDate) {
        this.stockOutDate = stockOutDate;
    }

    public StringFilter getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(StringFilter receiverId) {
        this.receiverId = receiverId;
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

    public LongFilter getStockInItemsId() {
        return stockInItemsId;
    }

    public void setStockInItemsId(LongFilter stockInItemsId) {
        this.stockInItemsId = stockInItemsId;
    }

    public LongFilter getStockStatusesId() {
        return stockStatusesId;
    }

    public void setStockStatusesId(LongFilter stockStatusesId) {
        this.stockStatusesId = stockStatusesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockOutItemCriteria that = (StockOutItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(containerTrackingId, that.containerTrackingId) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(stockOutBy, that.stockOutBy) &&
            Objects.equals(stockOutDate, that.stockOutDate) &&
            Objects.equals(receiverId, that.receiverId) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(productCategoriesId, that.productCategoriesId) &&
            Objects.equals(productsId, that.productsId) &&
            Objects.equals(inventoryLocationsId, that.inventoryLocationsId) &&
            Objects.equals(inventorySubLocationsId, that.inventorySubLocationsId) &&
            Objects.equals(stockInItemsId, that.stockInItemsId) &&
            Objects.equals(stockStatusesId, that.stockStatusesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        containerTrackingId,
        quantity,
        stockOutBy,
        stockOutDate,
        receiverId,
        remarks,
        productCategoriesId,
        productsId,
        inventoryLocationsId,
        inventorySubLocationsId,
        stockInItemsId,
        stockStatusesId
        );
    }

    @Override
    public String toString() {
        return "StockOutItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (containerTrackingId != null ? "containerTrackingId=" + containerTrackingId + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (stockOutBy != null ? "stockOutBy=" + stockOutBy + ", " : "") +
                (stockOutDate != null ? "stockOutDate=" + stockOutDate + ", " : "") +
                (receiverId != null ? "receiverId=" + receiverId + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (productCategoriesId != null ? "productCategoriesId=" + productCategoriesId + ", " : "") +
                (productsId != null ? "productsId=" + productsId + ", " : "") +
                (inventoryLocationsId != null ? "inventoryLocationsId=" + inventoryLocationsId + ", " : "") +
                (inventorySubLocationsId != null ? "inventorySubLocationsId=" + inventorySubLocationsId + ", " : "") +
                (stockInItemsId != null ? "stockInItemsId=" + stockInItemsId + ", " : "") +
                (stockStatusesId != null ? "stockStatusesId=" + stockStatusesId + ", " : "") +
            "}";
    }

}
