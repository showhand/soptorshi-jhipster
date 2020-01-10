package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.PackColor;
import org.soptorshi.domain.enumeration.ProductSpecification;
import org.soptorshi.domain.enumeration.SurfaceType;
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
     * Class for filtering ProductSpecification
     */
    public static class ProductSpecificationFilter extends Filter<ProductSpecification> {
    }
    /**
     * Class for filtering UnitOfMeasurements
     */
    public static class UnitOfMeasurementsFilter extends Filter<UnitOfMeasurements> {
    }
    /**
     * Class for filtering SurfaceType
     */
    public static class SurfaceTypeFilter extends Filter<SurfaceType> {
    }
    /**
     * Class for filtering PackColor
     */
    public static class PackColorFilter extends Filter<PackColor> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter taskNo;

    private ProductSpecificationFilter productSpecification;

    private StringFilter spSize;

    private BigDecimalFilter offeredQuantity;

    private UnitOfMeasurementsFilter offeredUnit;

    private BigDecimalFilter offeredUnitPrice;

    private BigDecimalFilter offeredTotalPrice;

    private IntegerFilter spGlazing;

    private SurfaceTypeFilter spSurfaceType;

    private StringFilter spOthersDescription;

    private StringFilter spSticker;

    private StringFilter spLabel;

    private BigDecimalFilter spQtyInPack;

    private BigDecimalFilter spQtyInMc;

    private PackColorFilter ipColor;

    private StringFilter ipSize;

    private StringFilter ipSticker;

    private StringFilter ipLabel;

    private BigDecimalFilter ipQtyInMc;

    private BigDecimalFilter ipCost;

    private PackColorFilter mcColor;

    private StringFilter mcPly;

    private StringFilter mcSize;

    private StringFilter mcSticker;

    private StringFilter mcLabel;

    private BigDecimalFilter mcCost;

    private StringFilter cylColor;

    private StringFilter cylSize;

    private BigDecimalFilter cylQty;

    private BigDecimalFilter cylCost;

    private BigDecimalFilter buyingQuantity;

    private UnitOfMeasurementsFilter buyingUnit;

    private BigDecimalFilter buyingUnitPrice;

    private BigDecimalFilter buyingPrice;

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

    public IntegerFilter getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(IntegerFilter taskNo) {
        this.taskNo = taskNo;
    }

    public ProductSpecificationFilter getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(ProductSpecificationFilter productSpecification) {
        this.productSpecification = productSpecification;
    }

    public StringFilter getSpSize() {
        return spSize;
    }

    public void setSpSize(StringFilter spSize) {
        this.spSize = spSize;
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

    public IntegerFilter getSpGlazing() {
        return spGlazing;
    }

    public void setSpGlazing(IntegerFilter spGlazing) {
        this.spGlazing = spGlazing;
    }

    public SurfaceTypeFilter getSpSurfaceType() {
        return spSurfaceType;
    }

    public void setSpSurfaceType(SurfaceTypeFilter spSurfaceType) {
        this.spSurfaceType = spSurfaceType;
    }

    public StringFilter getSpOthersDescription() {
        return spOthersDescription;
    }

    public void setSpOthersDescription(StringFilter spOthersDescription) {
        this.spOthersDescription = spOthersDescription;
    }

    public StringFilter getSpSticker() {
        return spSticker;
    }

    public void setSpSticker(StringFilter spSticker) {
        this.spSticker = spSticker;
    }

    public StringFilter getSpLabel() {
        return spLabel;
    }

    public void setSpLabel(StringFilter spLabel) {
        this.spLabel = spLabel;
    }

    public BigDecimalFilter getSpQtyInPack() {
        return spQtyInPack;
    }

    public void setSpQtyInPack(BigDecimalFilter spQtyInPack) {
        this.spQtyInPack = spQtyInPack;
    }

    public BigDecimalFilter getSpQtyInMc() {
        return spQtyInMc;
    }

    public void setSpQtyInMc(BigDecimalFilter spQtyInMc) {
        this.spQtyInMc = spQtyInMc;
    }

    public PackColorFilter getIpColor() {
        return ipColor;
    }

    public void setIpColor(PackColorFilter ipColor) {
        this.ipColor = ipColor;
    }

    public StringFilter getIpSize() {
        return ipSize;
    }

    public void setIpSize(StringFilter ipSize) {
        this.ipSize = ipSize;
    }

    public StringFilter getIpSticker() {
        return ipSticker;
    }

    public void setIpSticker(StringFilter ipSticker) {
        this.ipSticker = ipSticker;
    }

    public StringFilter getIpLabel() {
        return ipLabel;
    }

    public void setIpLabel(StringFilter ipLabel) {
        this.ipLabel = ipLabel;
    }

    public BigDecimalFilter getIpQtyInMc() {
        return ipQtyInMc;
    }

    public void setIpQtyInMc(BigDecimalFilter ipQtyInMc) {
        this.ipQtyInMc = ipQtyInMc;
    }

    public BigDecimalFilter getIpCost() {
        return ipCost;
    }

    public void setIpCost(BigDecimalFilter ipCost) {
        this.ipCost = ipCost;
    }

    public PackColorFilter getMcColor() {
        return mcColor;
    }

    public void setMcColor(PackColorFilter mcColor) {
        this.mcColor = mcColor;
    }

    public StringFilter getMcPly() {
        return mcPly;
    }

    public void setMcPly(StringFilter mcPly) {
        this.mcPly = mcPly;
    }

    public StringFilter getMcSize() {
        return mcSize;
    }

    public void setMcSize(StringFilter mcSize) {
        this.mcSize = mcSize;
    }

    public StringFilter getMcSticker() {
        return mcSticker;
    }

    public void setMcSticker(StringFilter mcSticker) {
        this.mcSticker = mcSticker;
    }

    public StringFilter getMcLabel() {
        return mcLabel;
    }

    public void setMcLabel(StringFilter mcLabel) {
        this.mcLabel = mcLabel;
    }

    public BigDecimalFilter getMcCost() {
        return mcCost;
    }

    public void setMcCost(BigDecimalFilter mcCost) {
        this.mcCost = mcCost;
    }

    public StringFilter getCylColor() {
        return cylColor;
    }

    public void setCylColor(StringFilter cylColor) {
        this.cylColor = cylColor;
    }

    public StringFilter getCylSize() {
        return cylSize;
    }

    public void setCylSize(StringFilter cylSize) {
        this.cylSize = cylSize;
    }

    public BigDecimalFilter getCylQty() {
        return cylQty;
    }

    public void setCylQty(BigDecimalFilter cylQty) {
        this.cylQty = cylQty;
    }

    public BigDecimalFilter getCylCost() {
        return cylCost;
    }

    public void setCylCost(BigDecimalFilter cylCost) {
        this.cylCost = cylCost;
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

    public BigDecimalFilter getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimalFilter buyingPrice) {
        this.buyingPrice = buyingPrice;
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
            Objects.equals(taskNo, that.taskNo) &&
            Objects.equals(productSpecification, that.productSpecification) &&
            Objects.equals(spSize, that.spSize) &&
            Objects.equals(offeredQuantity, that.offeredQuantity) &&
            Objects.equals(offeredUnit, that.offeredUnit) &&
            Objects.equals(offeredUnitPrice, that.offeredUnitPrice) &&
            Objects.equals(offeredTotalPrice, that.offeredTotalPrice) &&
            Objects.equals(spGlazing, that.spGlazing) &&
            Objects.equals(spSurfaceType, that.spSurfaceType) &&
            Objects.equals(spOthersDescription, that.spOthersDescription) &&
            Objects.equals(spSticker, that.spSticker) &&
            Objects.equals(spLabel, that.spLabel) &&
            Objects.equals(spQtyInPack, that.spQtyInPack) &&
            Objects.equals(spQtyInMc, that.spQtyInMc) &&
            Objects.equals(ipColor, that.ipColor) &&
            Objects.equals(ipSize, that.ipSize) &&
            Objects.equals(ipSticker, that.ipSticker) &&
            Objects.equals(ipLabel, that.ipLabel) &&
            Objects.equals(ipQtyInMc, that.ipQtyInMc) &&
            Objects.equals(ipCost, that.ipCost) &&
            Objects.equals(mcColor, that.mcColor) &&
            Objects.equals(mcPly, that.mcPly) &&
            Objects.equals(mcSize, that.mcSize) &&
            Objects.equals(mcSticker, that.mcSticker) &&
            Objects.equals(mcLabel, that.mcLabel) &&
            Objects.equals(mcCost, that.mcCost) &&
            Objects.equals(cylColor, that.cylColor) &&
            Objects.equals(cylSize, that.cylSize) &&
            Objects.equals(cylQty, that.cylQty) &&
            Objects.equals(cylCost, that.cylCost) &&
            Objects.equals(buyingQuantity, that.buyingQuantity) &&
            Objects.equals(buyingUnit, that.buyingUnit) &&
            Objects.equals(buyingUnitPrice, that.buyingUnitPrice) &&
            Objects.equals(buyingPrice, that.buyingPrice) &&
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
        taskNo,
        productSpecification,
        spSize,
        offeredQuantity,
        offeredUnit,
        offeredUnitPrice,
        offeredTotalPrice,
        spGlazing,
        spSurfaceType,
        spOthersDescription,
        spSticker,
        spLabel,
        spQtyInPack,
        spQtyInMc,
        ipColor,
        ipSize,
        ipSticker,
        ipLabel,
        ipQtyInMc,
        ipCost,
        mcColor,
        mcPly,
        mcSize,
        mcSticker,
        mcLabel,
        mcCost,
        cylColor,
        cylSize,
        cylQty,
        cylCost,
        buyingQuantity,
        buyingUnit,
        buyingUnitPrice,
        buyingPrice,
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
                (taskNo != null ? "taskNo=" + taskNo + ", " : "") +
                (productSpecification != null ? "productSpecification=" + productSpecification + ", " : "") +
                (spSize != null ? "spSize=" + spSize + ", " : "") +
                (offeredQuantity != null ? "offeredQuantity=" + offeredQuantity + ", " : "") +
                (offeredUnit != null ? "offeredUnit=" + offeredUnit + ", " : "") +
                (offeredUnitPrice != null ? "offeredUnitPrice=" + offeredUnitPrice + ", " : "") +
                (offeredTotalPrice != null ? "offeredTotalPrice=" + offeredTotalPrice + ", " : "") +
                (spGlazing != null ? "spGlazing=" + spGlazing + ", " : "") +
                (spSurfaceType != null ? "spSurfaceType=" + spSurfaceType + ", " : "") +
                (spOthersDescription != null ? "spOthersDescription=" + spOthersDescription + ", " : "") +
                (spSticker != null ? "spSticker=" + spSticker + ", " : "") +
                (spLabel != null ? "spLabel=" + spLabel + ", " : "") +
                (spQtyInPack != null ? "spQtyInPack=" + spQtyInPack + ", " : "") +
                (spQtyInMc != null ? "spQtyInMc=" + spQtyInMc + ", " : "") +
                (ipColor != null ? "ipColor=" + ipColor + ", " : "") +
                (ipSize != null ? "ipSize=" + ipSize + ", " : "") +
                (ipSticker != null ? "ipSticker=" + ipSticker + ", " : "") +
                (ipLabel != null ? "ipLabel=" + ipLabel + ", " : "") +
                (ipQtyInMc != null ? "ipQtyInMc=" + ipQtyInMc + ", " : "") +
                (ipCost != null ? "ipCost=" + ipCost + ", " : "") +
                (mcColor != null ? "mcColor=" + mcColor + ", " : "") +
                (mcPly != null ? "mcPly=" + mcPly + ", " : "") +
                (mcSize != null ? "mcSize=" + mcSize + ", " : "") +
                (mcSticker != null ? "mcSticker=" + mcSticker + ", " : "") +
                (mcLabel != null ? "mcLabel=" + mcLabel + ", " : "") +
                (mcCost != null ? "mcCost=" + mcCost + ", " : "") +
                (cylColor != null ? "cylColor=" + cylColor + ", " : "") +
                (cylSize != null ? "cylSize=" + cylSize + ", " : "") +
                (cylQty != null ? "cylQty=" + cylQty + ", " : "") +
                (cylCost != null ? "cylCost=" + cylCost + ", " : "") +
                (buyingQuantity != null ? "buyingQuantity=" + buyingQuantity + ", " : "") +
                (buyingUnit != null ? "buyingUnit=" + buyingUnit + ", " : "") +
                (buyingUnitPrice != null ? "buyingUnitPrice=" + buyingUnitPrice + ", " : "") +
                (buyingPrice != null ? "buyingPrice=" + buyingPrice + ", " : "") +
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
