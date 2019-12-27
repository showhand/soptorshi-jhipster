package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the CommercialProductInfo entity. This class is used in CommercialProductInfoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-product-infos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialProductInfoCriteria implements Serializable {
    /**
     * Class for filtering UnitOfMeasurements
     */
    public static class UnitOfMeasurementsFilter extends Filter<UnitOfMeasurements> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter serialNo;

    private StringFilter packagingDescription;

    private StringFilter othersDescription;

    private BigDecimalFilter offeredQuantity;

    private UnitOfMeasurementsFilter offeredUnit;

    private BigDecimalFilter offeredUnitPrice;

    private BigDecimalFilter offeredTotalPrice;

    private BigDecimalFilter buyingQuantity;

    private UnitOfMeasurementsFilter buyingUnit;

    private BigDecimalFilter buyingUnitPrice;

    private BigDecimalFilter buyingTotalPrice;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter commercialBudgetId;

    private LongFilter productCategoriesId;

    private LongFilter productsId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(IntegerFilter serialNo) {
        this.serialNo = serialNo;
    }

    public StringFilter getPackagingDescription() {
        return packagingDescription;
    }

    public void setPackagingDescription(StringFilter packagingDescription) {
        this.packagingDescription = packagingDescription;
    }

    public StringFilter getOthersDescription() {
        return othersDescription;
    }

    public void setOthersDescription(StringFilter othersDescription) {
        this.othersDescription = othersDescription;
    }

    public BigDecimalFilter getOfferedQuantity() {
        return offeredQuantity;
    }

    public void setOfferedQuantity(BigDecimalFilter offeredQuantity) {
        this.offeredQuantity = offeredQuantity;
    }

    public UnitOfMeasurementsFilter getOfferedUnit() {
        return offeredUnit;
    }

    public void setOfferedUnit(UnitOfMeasurementsFilter offeredUnit) {
        this.offeredUnit = offeredUnit;
    }

    public BigDecimalFilter getOfferedUnitPrice() {
        return offeredUnitPrice;
    }

    public void setOfferedUnitPrice(BigDecimalFilter offeredUnitPrice) {
        this.offeredUnitPrice = offeredUnitPrice;
    }

    public BigDecimalFilter getOfferedTotalPrice() {
        return offeredTotalPrice;
    }

    public void setOfferedTotalPrice(BigDecimalFilter offeredTotalPrice) {
        this.offeredTotalPrice = offeredTotalPrice;
    }

    public BigDecimalFilter getBuyingQuantity() {
        return buyingQuantity;
    }

    public void setBuyingQuantity(BigDecimalFilter buyingQuantity) {
        this.buyingQuantity = buyingQuantity;
    }

    public UnitOfMeasurementsFilter getBuyingUnit() {
        return buyingUnit;
    }

    public void setBuyingUnit(UnitOfMeasurementsFilter buyingUnit) {
        this.buyingUnit = buyingUnit;
    }

    public BigDecimalFilter getBuyingUnitPrice() {
        return buyingUnitPrice;
    }

    public void setBuyingUnitPrice(BigDecimalFilter buyingUnitPrice) {
        this.buyingUnitPrice = buyingUnitPrice;
    }

    public BigDecimalFilter getBuyingTotalPrice() {
        return buyingTotalPrice;
    }

    public void setBuyingTotalPrice(BigDecimalFilter buyingTotalPrice) {
        this.buyingTotalPrice = buyingTotalPrice;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getCommercialBudgetId() {
        return commercialBudgetId;
    }

    public void setCommercialBudgetId(LongFilter commercialBudgetId) {
        this.commercialBudgetId = commercialBudgetId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialProductInfoCriteria that = (CommercialProductInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serialNo, that.serialNo) &&
            Objects.equals(packagingDescription, that.packagingDescription) &&
            Objects.equals(othersDescription, that.othersDescription) &&
            Objects.equals(offeredQuantity, that.offeredQuantity) &&
            Objects.equals(offeredUnit, that.offeredUnit) &&
            Objects.equals(offeredUnitPrice, that.offeredUnitPrice) &&
            Objects.equals(offeredTotalPrice, that.offeredTotalPrice) &&
            Objects.equals(buyingQuantity, that.buyingQuantity) &&
            Objects.equals(buyingUnit, that.buyingUnit) &&
            Objects.equals(buyingUnitPrice, that.buyingUnitPrice) &&
            Objects.equals(buyingTotalPrice, that.buyingTotalPrice) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialBudgetId, that.commercialBudgetId) &&
            Objects.equals(productCategoriesId, that.productCategoriesId) &&
            Objects.equals(productsId, that.productsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serialNo,
        packagingDescription,
        othersDescription,
        offeredQuantity,
        offeredUnit,
        offeredUnitPrice,
        offeredTotalPrice,
        buyingQuantity,
        buyingUnit,
        buyingUnitPrice,
        buyingTotalPrice,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        commercialBudgetId,
        productCategoriesId,
        productsId
        );
    }

    @Override
    public String toString() {
        return "CommercialProductInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serialNo != null ? "serialNo=" + serialNo + ", " : "") +
                (packagingDescription != null ? "packagingDescription=" + packagingDescription + ", " : "") +
                (othersDescription != null ? "othersDescription=" + othersDescription + ", " : "") +
                (offeredQuantity != null ? "offeredQuantity=" + offeredQuantity + ", " : "") +
                (offeredUnit != null ? "offeredUnit=" + offeredUnit + ", " : "") +
                (offeredUnitPrice != null ? "offeredUnitPrice=" + offeredUnitPrice + ", " : "") +
                (offeredTotalPrice != null ? "offeredTotalPrice=" + offeredTotalPrice + ", " : "") +
                (buyingQuantity != null ? "buyingQuantity=" + buyingQuantity + ", " : "") +
                (buyingUnit != null ? "buyingUnit=" + buyingUnit + ", " : "") +
                (buyingUnitPrice != null ? "buyingUnitPrice=" + buyingUnitPrice + ", " : "") +
                (buyingTotalPrice != null ? "buyingTotalPrice=" + buyingTotalPrice + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialBudgetId != null ? "commercialBudgetId=" + commercialBudgetId + ", " : "") +
                (productCategoriesId != null ? "productCategoriesId=" + productCategoriesId + ", " : "") +
                (productsId != null ? "productsId=" + productsId + ", " : "") +
            "}";
    }

}
