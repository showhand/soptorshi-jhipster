package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.PackColor;
import org.soptorshi.domain.enumeration.ProductSpecification;
import org.soptorshi.domain.enumeration.SurfaceType;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the CommercialProductInfo entity.
 */
public class CommercialProductInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer taskNo;

    @NotNull
    private ProductSpecification productSpecification;

    private String spSize;

    @NotNull
    private BigDecimal offeredQuantity;

    @NotNull
    private UnitOfMeasurements offeredUnit;

    @NotNull
    private BigDecimal offeredUnitPrice;

    @NotNull
    private BigDecimal offeredTotalPrice;

    private Integer spGlazing;

    private SurfaceType spSurfaceType;

    private String spOthersDescription;

    private String spSticker;

    private String spLabel;

    private BigDecimal spQtyInPack;

    private BigDecimal spQtyInMc;

    private PackColor ipColor;

    private String ipSize;

    private String ipSticker;

    private String ipLabel;

    private BigDecimal ipQtyInMc;

    private BigDecimal ipCost;

    private PackColor mcColor;

    private String mcPly;

    private String mcSize;

    private String mcSticker;

    private String mcLabel;

    private BigDecimal mcCost;

    private String cylColor;

    private String cylSize;

    private BigDecimal cylQty;

    private BigDecimal cylCost;

    @NotNull
    private BigDecimal buyingQuantity;

    @NotNull
    private UnitOfMeasurements buyingUnit;

    @NotNull
    private BigDecimal buyingUnitPrice;

    @NotNull
    private BigDecimal buyingPrice;

    @NotNull
    private BigDecimal buyingTotalPrice;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    private Long commercialBudgetId;

    private String commercialBudgetBudgetNo;

    private Long productCategoriesId;

    private String productCategoriesName;

    private Long productsId;

    private String productsName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(Integer taskNo) {
        this.taskNo = taskNo;
    }

    public ProductSpecification getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getSpSize() {
        return spSize;
    }

    public void setSpSize(String spSize) {
        this.spSize = spSize;
    }

    public BigDecimal getOfferedQuantity() {
        return offeredQuantity;
    }

    public void setOfferedQuantity(BigDecimal offeredQuantity) {
        this.offeredQuantity = offeredQuantity;
    }

    public UnitOfMeasurements getOfferedUnit() {
        return offeredUnit;
    }

    public void setOfferedUnit(UnitOfMeasurements offeredUnit) {
        this.offeredUnit = offeredUnit;
    }

    public BigDecimal getOfferedUnitPrice() {
        return offeredUnitPrice;
    }

    public void setOfferedUnitPrice(BigDecimal offeredUnitPrice) {
        this.offeredUnitPrice = offeredUnitPrice;
    }

    public BigDecimal getOfferedTotalPrice() {
        return offeredTotalPrice;
    }

    public void setOfferedTotalPrice(BigDecimal offeredTotalPrice) {
        this.offeredTotalPrice = offeredTotalPrice;
    }

    public Integer getSpGlazing() {
        return spGlazing;
    }

    public void setSpGlazing(Integer spGlazing) {
        this.spGlazing = spGlazing;
    }

    public SurfaceType getSpSurfaceType() {
        return spSurfaceType;
    }

    public void setSpSurfaceType(SurfaceType spSurfaceType) {
        this.spSurfaceType = spSurfaceType;
    }

    public String getSpOthersDescription() {
        return spOthersDescription;
    }

    public void setSpOthersDescription(String spOthersDescription) {
        this.spOthersDescription = spOthersDescription;
    }

    public String getSpSticker() {
        return spSticker;
    }

    public void setSpSticker(String spSticker) {
        this.spSticker = spSticker;
    }

    public String getSpLabel() {
        return spLabel;
    }

    public void setSpLabel(String spLabel) {
        this.spLabel = spLabel;
    }

    public BigDecimal getSpQtyInPack() {
        return spQtyInPack;
    }

    public void setSpQtyInPack(BigDecimal spQtyInPack) {
        this.spQtyInPack = spQtyInPack;
    }

    public BigDecimal getSpQtyInMc() {
        return spQtyInMc;
    }

    public void setSpQtyInMc(BigDecimal spQtyInMc) {
        this.spQtyInMc = spQtyInMc;
    }

    public PackColor getIpColor() {
        return ipColor;
    }

    public void setIpColor(PackColor ipColor) {
        this.ipColor = ipColor;
    }

    public String getIpSize() {
        return ipSize;
    }

    public void setIpSize(String ipSize) {
        this.ipSize = ipSize;
    }

    public String getIpSticker() {
        return ipSticker;
    }

    public void setIpSticker(String ipSticker) {
        this.ipSticker = ipSticker;
    }

    public String getIpLabel() {
        return ipLabel;
    }

    public void setIpLabel(String ipLabel) {
        this.ipLabel = ipLabel;
    }

    public BigDecimal getIpQtyInMc() {
        return ipQtyInMc;
    }

    public void setIpQtyInMc(BigDecimal ipQtyInMc) {
        this.ipQtyInMc = ipQtyInMc;
    }

    public BigDecimal getIpCost() {
        return ipCost;
    }

    public void setIpCost(BigDecimal ipCost) {
        this.ipCost = ipCost;
    }

    public PackColor getMcColor() {
        return mcColor;
    }

    public void setMcColor(PackColor mcColor) {
        this.mcColor = mcColor;
    }

    public String getMcPly() {
        return mcPly;
    }

    public void setMcPly(String mcPly) {
        this.mcPly = mcPly;
    }

    public String getMcSize() {
        return mcSize;
    }

    public void setMcSize(String mcSize) {
        this.mcSize = mcSize;
    }

    public String getMcSticker() {
        return mcSticker;
    }

    public void setMcSticker(String mcSticker) {
        this.mcSticker = mcSticker;
    }

    public String getMcLabel() {
        return mcLabel;
    }

    public void setMcLabel(String mcLabel) {
        this.mcLabel = mcLabel;
    }

    public BigDecimal getMcCost() {
        return mcCost;
    }

    public void setMcCost(BigDecimal mcCost) {
        this.mcCost = mcCost;
    }

    public String getCylColor() {
        return cylColor;
    }

    public void setCylColor(String cylColor) {
        this.cylColor = cylColor;
    }

    public String getCylSize() {
        return cylSize;
    }

    public void setCylSize(String cylSize) {
        this.cylSize = cylSize;
    }

    public BigDecimal getCylQty() {
        return cylQty;
    }

    public void setCylQty(BigDecimal cylQty) {
        this.cylQty = cylQty;
    }

    public BigDecimal getCylCost() {
        return cylCost;
    }

    public void setCylCost(BigDecimal cylCost) {
        this.cylCost = cylCost;
    }

    public BigDecimal getBuyingQuantity() {
        return buyingQuantity;
    }

    public void setBuyingQuantity(BigDecimal buyingQuantity) {
        this.buyingQuantity = buyingQuantity;
    }

    public UnitOfMeasurements getBuyingUnit() {
        return buyingUnit;
    }

    public void setBuyingUnit(UnitOfMeasurements buyingUnit) {
        this.buyingUnit = buyingUnit;
    }

    public BigDecimal getBuyingUnitPrice() {
        return buyingUnitPrice;
    }

    public void setBuyingUnitPrice(BigDecimal buyingUnitPrice) {
        this.buyingUnitPrice = buyingUnitPrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getBuyingTotalPrice() {
        return buyingTotalPrice;
    }

    public void setBuyingTotalPrice(BigDecimal buyingTotalPrice) {
        this.buyingTotalPrice = buyingTotalPrice;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getCommercialBudgetId() {
        return commercialBudgetId;
    }

    public void setCommercialBudgetId(Long commercialBudgetId) {
        this.commercialBudgetId = commercialBudgetId;
    }

    public String getCommercialBudgetBudgetNo() {
        return commercialBudgetBudgetNo;
    }

    public void setCommercialBudgetBudgetNo(String commercialBudgetBudgetNo) {
        this.commercialBudgetBudgetNo = commercialBudgetBudgetNo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialProductInfoDTO commercialProductInfoDTO = (CommercialProductInfoDTO) o;
        if (commercialProductInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialProductInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialProductInfoDTO{" +
            "id=" + getId() +
            ", taskNo=" + getTaskNo() +
            ", productSpecification='" + getProductSpecification() + "'" +
            ", spSize='" + getSpSize() + "'" +
            ", offeredQuantity=" + getOfferedQuantity() +
            ", offeredUnit='" + getOfferedUnit() + "'" +
            ", offeredUnitPrice=" + getOfferedUnitPrice() +
            ", offeredTotalPrice=" + getOfferedTotalPrice() +
            ", spGlazing=" + getSpGlazing() +
            ", spSurfaceType='" + getSpSurfaceType() + "'" +
            ", spOthersDescription='" + getSpOthersDescription() + "'" +
            ", spSticker='" + getSpSticker() + "'" +
            ", spLabel='" + getSpLabel() + "'" +
            ", spQtyInPack=" + getSpQtyInPack() +
            ", spQtyInMc=" + getSpQtyInMc() +
            ", ipColor='" + getIpColor() + "'" +
            ", ipSize='" + getIpSize() + "'" +
            ", ipSticker='" + getIpSticker() + "'" +
            ", ipLabel='" + getIpLabel() + "'" +
            ", ipQtyInMc=" + getIpQtyInMc() +
            ", ipCost=" + getIpCost() +
            ", mcColor='" + getMcColor() + "'" +
            ", mcPly='" + getMcPly() + "'" +
            ", mcSize='" + getMcSize() + "'" +
            ", mcSticker='" + getMcSticker() + "'" +
            ", mcLabel='" + getMcLabel() + "'" +
            ", mcCost=" + getMcCost() +
            ", cylColor='" + getCylColor() + "'" +
            ", cylSize='" + getCylSize() + "'" +
            ", cylQty=" + getCylQty() +
            ", cylCost=" + getCylCost() +
            ", buyingQuantity=" + getBuyingQuantity() +
            ", buyingUnit='" + getBuyingUnit() + "'" +
            ", buyingUnitPrice=" + getBuyingUnitPrice() +
            ", buyingPrice=" + getBuyingPrice() +
            ", buyingTotalPrice=" + getBuyingTotalPrice() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", commercialBudget=" + getCommercialBudgetId() +
            ", commercialBudget='" + getCommercialBudgetBudgetNo() + "'" +
            ", productCategories=" + getProductCategoriesId() +
            ", productCategories='" + getProductCategoriesName() + "'" +
            ", products=" + getProductsId() +
            ", products='" + getProductsName() + "'" +
            "}";
    }
}
