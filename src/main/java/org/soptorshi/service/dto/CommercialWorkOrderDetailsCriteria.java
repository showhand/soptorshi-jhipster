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
 * Criteria class for the CommercialWorkOrderDetails entity. This class is used in CommercialWorkOrderDetailsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-work-order-details?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialWorkOrderDetailsCriteria implements Serializable {
    /**
     * Class for filtering CommercialCurrency
     */
    public static class CommercialCurrencyFilter extends Filter<CommercialCurrency> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter goods;

    private StringFilter reason;

    private StringFilter size;

    private StringFilter color;

    private DoubleFilter quantity;

    private CommercialCurrencyFilter currencyType;

    private DoubleFilter rate;

    private StringFilter createdBy;

    private LocalDateFilter createdOn;

    private StringFilter updatedBy;

    private LocalDateFilter updatedOn;

    private LongFilter commercialWorkOrderId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getGoods() {
        return goods;
    }

    public void setGoods(StringFilter goods) {
        this.goods = goods;
    }

    public StringFilter getReason() {
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public StringFilter getSize() {
        return size;
    }

    public void setSize(StringFilter size) {
        this.size = size;
    }

    public StringFilter getColor() {
        return color;
    }

    public void setColor(StringFilter color) {
        this.color = color;
    }

    public DoubleFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(DoubleFilter quantity) {
        this.quantity = quantity;
    }

    public CommercialCurrencyFilter getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CommercialCurrencyFilter currencyType) {
        this.currencyType = currencyType;
    }

    public DoubleFilter getRate() {
        return rate;
    }

    public void setRate(DoubleFilter rate) {
        this.rate = rate;
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

    public LongFilter getCommercialWorkOrderId() {
        return commercialWorkOrderId;
    }

    public void setCommercialWorkOrderId(LongFilter commercialWorkOrderId) {
        this.commercialWorkOrderId = commercialWorkOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialWorkOrderDetailsCriteria that = (CommercialWorkOrderDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(goods, that.goods) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(size, that.size) &&
            Objects.equals(color, that.color) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(currencyType, that.currencyType) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialWorkOrderId, that.commercialWorkOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        goods,
        reason,
        size,
        color,
        quantity,
        currencyType,
        rate,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        commercialWorkOrderId
        );
    }

    @Override
    public String toString() {
        return "CommercialWorkOrderDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (goods != null ? "goods=" + goods + ", " : "") +
                (reason != null ? "reason=" + reason + ", " : "") +
                (size != null ? "size=" + size + ", " : "") +
                (color != null ? "color=" + color + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (currencyType != null ? "currencyType=" + currencyType + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialWorkOrderId != null ? "commercialWorkOrderId=" + commercialWorkOrderId + ", " : "") +
            "}";
    }

}
