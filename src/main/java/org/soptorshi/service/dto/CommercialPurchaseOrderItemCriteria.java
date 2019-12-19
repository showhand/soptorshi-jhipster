package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.CommercialCurrency;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the CommercialPurchaseOrderItem entity. This class is used in CommercialPurchaseOrderItemResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-purchase-order-items?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialPurchaseOrderItemCriteria implements Serializable {
    /**
     * Class for filtering CommercialCurrency
     */
    public static class CommercialCurrencyFilter extends Filter<CommercialCurrency> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter goodsOrServices;

    private StringFilter descriptionOfGoodsOrServices;

    private StringFilter packaging;

    private StringFilter sizeOrGrade;

    private DoubleFilter qtyOrMc;

    private DoubleFilter qtyOrKgs;

    private DoubleFilter rateOrKg;

    private CommercialCurrencyFilter currencyType;

    private DoubleFilter total;

    private StringFilter createdBy;

    private LocalDateFilter createdOn;

    private StringFilter updatedBy;

    private LocalDateFilter updatedOn;

    private LongFilter commercialPurchaseOrderId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getGoodsOrServices() {
        return goodsOrServices;
    }

    public void setGoodsOrServices(StringFilter goodsOrServices) {
        this.goodsOrServices = goodsOrServices;
    }

    public StringFilter getDescriptionOfGoodsOrServices() {
        return descriptionOfGoodsOrServices;
    }

    public void setDescriptionOfGoodsOrServices(StringFilter descriptionOfGoodsOrServices) {
        this.descriptionOfGoodsOrServices = descriptionOfGoodsOrServices;
    }

    public StringFilter getPackaging() {
        return packaging;
    }

    public void setPackaging(StringFilter packaging) {
        this.packaging = packaging;
    }

    public StringFilter getSizeOrGrade() {
        return sizeOrGrade;
    }

    public void setSizeOrGrade(StringFilter sizeOrGrade) {
        this.sizeOrGrade = sizeOrGrade;
    }

    public DoubleFilter getQtyOrMc() {
        return qtyOrMc;
    }

    public void setQtyOrMc(DoubleFilter qtyOrMc) {
        this.qtyOrMc = qtyOrMc;
    }

    public DoubleFilter getQtyOrKgs() {
        return qtyOrKgs;
    }

    public void setQtyOrKgs(DoubleFilter qtyOrKgs) {
        this.qtyOrKgs = qtyOrKgs;
    }

    public DoubleFilter getRateOrKg() {
        return rateOrKg;
    }

    public void setRateOrKg(DoubleFilter rateOrKg) {
        this.rateOrKg = rateOrKg;
    }

    public CommercialCurrencyFilter getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CommercialCurrencyFilter currencyType) {
        this.currencyType = currencyType;
    }

    public DoubleFilter getTotal() {
        return total;
    }

    public void setTotal(DoubleFilter total) {
        this.total = total;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getCommercialPurchaseOrderId() {
        return commercialPurchaseOrderId;
    }

    public void setCommercialPurchaseOrderId(LongFilter commercialPurchaseOrderId) {
        this.commercialPurchaseOrderId = commercialPurchaseOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialPurchaseOrderItemCriteria that = (CommercialPurchaseOrderItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(goodsOrServices, that.goodsOrServices) &&
            Objects.equals(descriptionOfGoodsOrServices, that.descriptionOfGoodsOrServices) &&
            Objects.equals(packaging, that.packaging) &&
            Objects.equals(sizeOrGrade, that.sizeOrGrade) &&
            Objects.equals(qtyOrMc, that.qtyOrMc) &&
            Objects.equals(qtyOrKgs, that.qtyOrKgs) &&
            Objects.equals(rateOrKg, that.rateOrKg) &&
            Objects.equals(currencyType, that.currencyType) &&
            Objects.equals(total, that.total) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialPurchaseOrderId, that.commercialPurchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        goodsOrServices,
        descriptionOfGoodsOrServices,
        packaging,
        sizeOrGrade,
        qtyOrMc,
        qtyOrKgs,
        rateOrKg,
        currencyType,
        total,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        commercialPurchaseOrderId
        );
    }

    @Override
    public String toString() {
        return "CommercialPurchaseOrderItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (goodsOrServices != null ? "goodsOrServices=" + goodsOrServices + ", " : "") +
                (descriptionOfGoodsOrServices != null ? "descriptionOfGoodsOrServices=" + descriptionOfGoodsOrServices + ", " : "") +
                (packaging != null ? "packaging=" + packaging + ", " : "") +
                (sizeOrGrade != null ? "sizeOrGrade=" + sizeOrGrade + ", " : "") +
                (qtyOrMc != null ? "qtyOrMc=" + qtyOrMc + ", " : "") +
                (qtyOrKgs != null ? "qtyOrKgs=" + qtyOrKgs + ", " : "") +
                (rateOrKg != null ? "rateOrKg=" + rateOrKg + ", " : "") +
                (currencyType != null ? "currencyType=" + currencyType + ", " : "") +
                (total != null ? "total=" + total + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialPurchaseOrderId != null ? "commercialPurchaseOrderId=" + commercialPurchaseOrderId + ", " : "") +
            "}";
    }

}
