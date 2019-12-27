package org.soptorshi.service.dto;

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
    private Integer serialNo;

    private String packagingDescription;

    private String othersDescription;

    @NotNull
    private BigDecimal offeredQuantity;

    @NotNull
    private UnitOfMeasurements offeredUnit;

    @NotNull
    private BigDecimal offeredUnitPrice;

    @NotNull
    private BigDecimal offeredTotalPrice;

    @NotNull
    private BigDecimal buyingQuantity;

    @NotNull
    private UnitOfMeasurements buyingUnit;

    @NotNull
    private BigDecimal buyingUnitPrice;

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

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getPackagingDescription() {
        return packagingDescription;
    }

    public void setPackagingDescription(String packagingDescription) {
        this.packagingDescription = packagingDescription;
    }

    public String getOthersDescription() {
        return othersDescription;
    }

    public void setOthersDescription(String othersDescription) {
        this.othersDescription = othersDescription;
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
            ", serialNo=" + getSerialNo() +
            ", packagingDescription='" + getPackagingDescription() + "'" +
            ", othersDescription='" + getOthersDescription() + "'" +
            ", offeredQuantity=" + getOfferedQuantity() +
            ", offeredUnit='" + getOfferedUnit() + "'" +
            ", offeredUnitPrice=" + getOfferedUnitPrice() +
            ", offeredTotalPrice=" + getOfferedTotalPrice() +
            ", buyingQuantity=" + getBuyingQuantity() +
            ", buyingUnit='" + getBuyingUnit() + "'" +
            ", buyingUnitPrice=" + getBuyingUnitPrice() +
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
