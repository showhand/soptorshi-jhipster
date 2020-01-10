package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.PackColor;
import org.soptorshi.domain.enumeration.ProductSpecification;
import org.soptorshi.domain.enumeration.SurfaceType;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A CommercialProductInfo.
 */
@Entity
@Table(name = "commercial_product_info")
@Document(indexName = "commercialproductinfo")
public class CommercialProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "task_no", nullable = false)
    private Integer taskNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "product_specification", nullable = false)
    private ProductSpecification productSpecification;

    @Column(name = "sp_size")
    private String spSize;

    @NotNull
    @Column(name = "offered_quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal offeredQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "offered_unit", nullable = false)
    private UnitOfMeasurements offeredUnit;

    @NotNull
    @Column(name = "offered_unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal offeredUnitPrice;

    @NotNull
    @Column(name = "offered_total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal offeredTotalPrice;

    @Column(name = "sp_glazing")
    private Integer spGlazing;

    @Enumerated(EnumType.STRING)
    @Column(name = "sp_surface_type")
    private SurfaceType spSurfaceType;

    @Column(name = "sp_others_description")
    private String spOthersDescription;

    @Column(name = "sp_sticker")
    private String spSticker;

    @Column(name = "sp_label")
    private String spLabel;

    @Column(name = "sp_qty_in_pack", precision = 10, scale = 2)
    private BigDecimal spQtyInPack;

    @Column(name = "sp_qty_in_mc", precision = 10, scale = 2)
    private BigDecimal spQtyInMc;

    @Enumerated(EnumType.STRING)
    @Column(name = "ip_color")
    private PackColor ipColor;

    @Column(name = "ip_size")
    private String ipSize;

    @Column(name = "ip_sticker")
    private String ipSticker;

    @Column(name = "ip_label")
    private String ipLabel;

    @Column(name = "ip_qty_in_mc", precision = 10, scale = 2)
    private BigDecimal ipQtyInMc;

    @Column(name = "ip_cost", precision = 10, scale = 2)
    private BigDecimal ipCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "mc_color")
    private PackColor mcColor;

    @Column(name = "mc_ply")
    private String mcPly;

    @Column(name = "mc_size")
    private String mcSize;

    @Column(name = "mc_sticker")
    private String mcSticker;

    @Column(name = "mc_label")
    private String mcLabel;

    @Column(name = "mc_cost", precision = 10, scale = 2)
    private BigDecimal mcCost;

    @Column(name = "cyl_color")
    private String cylColor;

    @Column(name = "cyl_size")
    private String cylSize;

    @Column(name = "cyl_qty", precision = 10, scale = 2)
    private BigDecimal cylQty;

    @Column(name = "cyl_cost", precision = 10, scale = 2)
    private BigDecimal cylCost;

    @NotNull
    @Column(name = "buying_quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal buyingQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "buying_unit", nullable = false)
    private UnitOfMeasurements buyingUnit;

    @NotNull
    @Column(name = "buying_unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal buyingUnitPrice;

    @NotNull
    @Column(name = "buying_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal buyingPrice;

    @NotNull
    @Column(name = "buying_total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal buyingTotalPrice;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @ManyToOne
    @JsonIgnoreProperties("commercialProductInfos")
    private CommercialBudget commercialBudget;

    @ManyToOne
    @JsonIgnoreProperties("commercialProductInfos")
    private ProductCategory productCategories;

    @ManyToOne
    @JsonIgnoreProperties("commercialProductInfos")
    private Product products;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskNo() {
        return taskNo;
    }

    public CommercialProductInfo taskNo(Integer taskNo) {
        this.taskNo = taskNo;
        return this;
    }

    public void setTaskNo(Integer taskNo) {
        this.taskNo = taskNo;
    }

    public ProductSpecification getProductSpecification() {
        return productSpecification;
    }

    public CommercialProductInfo productSpecification(ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
        return this;
    }

    public void setProductSpecification(ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getSpSize() {
        return spSize;
    }

    public CommercialProductInfo spSize(String spSize) {
        this.spSize = spSize;
        return this;
    }

    public void setSpSize(String spSize) {
        this.spSize = spSize;
    }

    public BigDecimal getOfferedQuantity() {
        return offeredQuantity;
    }

    public CommercialProductInfo offeredQuantity(BigDecimal offeredQuantity) {
        this.offeredQuantity = offeredQuantity;
        return this;
    }

    public void setOfferedQuantity(BigDecimal offeredQuantity) {
        this.offeredQuantity = offeredQuantity;
    }

    public UnitOfMeasurements getOfferedUnit() {
        return offeredUnit;
    }

    public CommercialProductInfo offeredUnit(UnitOfMeasurements offeredUnit) {
        this.offeredUnit = offeredUnit;
        return this;
    }

    public void setOfferedUnit(UnitOfMeasurements offeredUnit) {
        this.offeredUnit = offeredUnit;
    }

    public BigDecimal getOfferedUnitPrice() {
        return offeredUnitPrice;
    }

    public CommercialProductInfo offeredUnitPrice(BigDecimal offeredUnitPrice) {
        this.offeredUnitPrice = offeredUnitPrice;
        return this;
    }

    public void setOfferedUnitPrice(BigDecimal offeredUnitPrice) {
        this.offeredUnitPrice = offeredUnitPrice;
    }

    public BigDecimal getOfferedTotalPrice() {
        return offeredTotalPrice;
    }

    public CommercialProductInfo offeredTotalPrice(BigDecimal offeredTotalPrice) {
        this.offeredTotalPrice = offeredTotalPrice;
        return this;
    }

    public void setOfferedTotalPrice(BigDecimal offeredTotalPrice) {
        this.offeredTotalPrice = offeredTotalPrice;
    }

    public Integer getSpGlazing() {
        return spGlazing;
    }

    public CommercialProductInfo spGlazing(Integer spGlazing) {
        this.spGlazing = spGlazing;
        return this;
    }

    public void setSpGlazing(Integer spGlazing) {
        this.spGlazing = spGlazing;
    }

    public SurfaceType getSpSurfaceType() {
        return spSurfaceType;
    }

    public CommercialProductInfo spSurfaceType(SurfaceType spSurfaceType) {
        this.spSurfaceType = spSurfaceType;
        return this;
    }

    public void setSpSurfaceType(SurfaceType spSurfaceType) {
        this.spSurfaceType = spSurfaceType;
    }

    public String getSpOthersDescription() {
        return spOthersDescription;
    }

    public CommercialProductInfo spOthersDescription(String spOthersDescription) {
        this.spOthersDescription = spOthersDescription;
        return this;
    }

    public void setSpOthersDescription(String spOthersDescription) {
        this.spOthersDescription = spOthersDescription;
    }

    public String getSpSticker() {
        return spSticker;
    }

    public CommercialProductInfo spSticker(String spSticker) {
        this.spSticker = spSticker;
        return this;
    }

    public void setSpSticker(String spSticker) {
        this.spSticker = spSticker;
    }

    public String getSpLabel() {
        return spLabel;
    }

    public CommercialProductInfo spLabel(String spLabel) {
        this.spLabel = spLabel;
        return this;
    }

    public void setSpLabel(String spLabel) {
        this.spLabel = spLabel;
    }

    public BigDecimal getSpQtyInPack() {
        return spQtyInPack;
    }

    public CommercialProductInfo spQtyInPack(BigDecimal spQtyInPack) {
        this.spQtyInPack = spQtyInPack;
        return this;
    }

    public void setSpQtyInPack(BigDecimal spQtyInPack) {
        this.spQtyInPack = spQtyInPack;
    }

    public BigDecimal getSpQtyInMc() {
        return spQtyInMc;
    }

    public CommercialProductInfo spQtyInMc(BigDecimal spQtyInMc) {
        this.spQtyInMc = spQtyInMc;
        return this;
    }

    public void setSpQtyInMc(BigDecimal spQtyInMc) {
        this.spQtyInMc = spQtyInMc;
    }

    public PackColor getIpColor() {
        return ipColor;
    }

    public CommercialProductInfo ipColor(PackColor ipColor) {
        this.ipColor = ipColor;
        return this;
    }

    public void setIpColor(PackColor ipColor) {
        this.ipColor = ipColor;
    }

    public String getIpSize() {
        return ipSize;
    }

    public CommercialProductInfo ipSize(String ipSize) {
        this.ipSize = ipSize;
        return this;
    }

    public void setIpSize(String ipSize) {
        this.ipSize = ipSize;
    }

    public String getIpSticker() {
        return ipSticker;
    }

    public CommercialProductInfo ipSticker(String ipSticker) {
        this.ipSticker = ipSticker;
        return this;
    }

    public void setIpSticker(String ipSticker) {
        this.ipSticker = ipSticker;
    }

    public String getIpLabel() {
        return ipLabel;
    }

    public CommercialProductInfo ipLabel(String ipLabel) {
        this.ipLabel = ipLabel;
        return this;
    }

    public void setIpLabel(String ipLabel) {
        this.ipLabel = ipLabel;
    }

    public BigDecimal getIpQtyInMc() {
        return ipQtyInMc;
    }

    public CommercialProductInfo ipQtyInMc(BigDecimal ipQtyInMc) {
        this.ipQtyInMc = ipQtyInMc;
        return this;
    }

    public void setIpQtyInMc(BigDecimal ipQtyInMc) {
        this.ipQtyInMc = ipQtyInMc;
    }

    public BigDecimal getIpCost() {
        return ipCost;
    }

    public CommercialProductInfo ipCost(BigDecimal ipCost) {
        this.ipCost = ipCost;
        return this;
    }

    public void setIpCost(BigDecimal ipCost) {
        this.ipCost = ipCost;
    }

    public PackColor getMcColor() {
        return mcColor;
    }

    public CommercialProductInfo mcColor(PackColor mcColor) {
        this.mcColor = mcColor;
        return this;
    }

    public void setMcColor(PackColor mcColor) {
        this.mcColor = mcColor;
    }

    public String getMcPly() {
        return mcPly;
    }

    public CommercialProductInfo mcPly(String mcPly) {
        this.mcPly = mcPly;
        return this;
    }

    public void setMcPly(String mcPly) {
        this.mcPly = mcPly;
    }

    public String getMcSize() {
        return mcSize;
    }

    public CommercialProductInfo mcSize(String mcSize) {
        this.mcSize = mcSize;
        return this;
    }

    public void setMcSize(String mcSize) {
        this.mcSize = mcSize;
    }

    public String getMcSticker() {
        return mcSticker;
    }

    public CommercialProductInfo mcSticker(String mcSticker) {
        this.mcSticker = mcSticker;
        return this;
    }

    public void setMcSticker(String mcSticker) {
        this.mcSticker = mcSticker;
    }

    public String getMcLabel() {
        return mcLabel;
    }

    public CommercialProductInfo mcLabel(String mcLabel) {
        this.mcLabel = mcLabel;
        return this;
    }

    public void setMcLabel(String mcLabel) {
        this.mcLabel = mcLabel;
    }

    public BigDecimal getMcCost() {
        return mcCost;
    }

    public CommercialProductInfo mcCost(BigDecimal mcCost) {
        this.mcCost = mcCost;
        return this;
    }

    public void setMcCost(BigDecimal mcCost) {
        this.mcCost = mcCost;
    }

    public String getCylColor() {
        return cylColor;
    }

    public CommercialProductInfo cylColor(String cylColor) {
        this.cylColor = cylColor;
        return this;
    }

    public void setCylColor(String cylColor) {
        this.cylColor = cylColor;
    }

    public String getCylSize() {
        return cylSize;
    }

    public CommercialProductInfo cylSize(String cylSize) {
        this.cylSize = cylSize;
        return this;
    }

    public void setCylSize(String cylSize) {
        this.cylSize = cylSize;
    }

    public BigDecimal getCylQty() {
        return cylQty;
    }

    public CommercialProductInfo cylQty(BigDecimal cylQty) {
        this.cylQty = cylQty;
        return this;
    }

    public void setCylQty(BigDecimal cylQty) {
        this.cylQty = cylQty;
    }

    public BigDecimal getCylCost() {
        return cylCost;
    }

    public CommercialProductInfo cylCost(BigDecimal cylCost) {
        this.cylCost = cylCost;
        return this;
    }

    public void setCylCost(BigDecimal cylCost) {
        this.cylCost = cylCost;
    }

    public BigDecimal getBuyingQuantity() {
        return buyingQuantity;
    }

    public CommercialProductInfo buyingQuantity(BigDecimal buyingQuantity) {
        this.buyingQuantity = buyingQuantity;
        return this;
    }

    public void setBuyingQuantity(BigDecimal buyingQuantity) {
        this.buyingQuantity = buyingQuantity;
    }

    public UnitOfMeasurements getBuyingUnit() {
        return buyingUnit;
    }

    public CommercialProductInfo buyingUnit(UnitOfMeasurements buyingUnit) {
        this.buyingUnit = buyingUnit;
        return this;
    }

    public void setBuyingUnit(UnitOfMeasurements buyingUnit) {
        this.buyingUnit = buyingUnit;
    }

    public BigDecimal getBuyingUnitPrice() {
        return buyingUnitPrice;
    }

    public CommercialProductInfo buyingUnitPrice(BigDecimal buyingUnitPrice) {
        this.buyingUnitPrice = buyingUnitPrice;
        return this;
    }

    public void setBuyingUnitPrice(BigDecimal buyingUnitPrice) {
        this.buyingUnitPrice = buyingUnitPrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public CommercialProductInfo buyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
        return this;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getBuyingTotalPrice() {
        return buyingTotalPrice;
    }

    public CommercialProductInfo buyingTotalPrice(BigDecimal buyingTotalPrice) {
        this.buyingTotalPrice = buyingTotalPrice;
        return this;
    }

    public void setBuyingTotalPrice(BigDecimal buyingTotalPrice) {
        this.buyingTotalPrice = buyingTotalPrice;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialProductInfo createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public CommercialProductInfo createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialProductInfo updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public CommercialProductInfo updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialBudget getCommercialBudget() {
        return commercialBudget;
    }

    public CommercialProductInfo commercialBudget(CommercialBudget commercialBudget) {
        this.commercialBudget = commercialBudget;
        return this;
    }

    public void setCommercialBudget(CommercialBudget commercialBudget) {
        this.commercialBudget = commercialBudget;
    }

    public ProductCategory getProductCategories() {
        return productCategories;
    }

    public CommercialProductInfo productCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
        return this;
    }

    public void setProductCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
    }

    public Product getProducts() {
        return products;
    }

    public CommercialProductInfo products(Product product) {
        this.products = product;
        return this;
    }

    public void setProducts(Product product) {
        this.products = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommercialProductInfo commercialProductInfo = (CommercialProductInfo) o;
        if (commercialProductInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialProductInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialProductInfo{" +
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
            "}";
    }
}
