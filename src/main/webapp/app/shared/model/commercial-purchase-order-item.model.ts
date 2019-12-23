import { Moment } from 'moment';

export const enum CommercialCurrency {
    BDT = 'BDT',
    US_DOLLAR = 'US_DOLLAR'
}

export interface ICommercialPurchaseOrderItem {
    id?: number;
    goodsOrServices?: string;
    descriptionOfGoodsOrServices?: string;
    packaging?: string;
    sizeOrGrade?: string;
    qtyOrMc?: number;
    qtyOrKgs?: number;
    rateOrKg?: number;
    currencyType?: CommercialCurrency;
    total?: number;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    commercialPurchaseOrderPurchaseOrderNo?: string;
    commercialPurchaseOrderId?: number;
}

export class CommercialPurchaseOrderItem implements ICommercialPurchaseOrderItem {
    constructor(
        public id?: number,
        public goodsOrServices?: string,
        public descriptionOfGoodsOrServices?: string,
        public packaging?: string,
        public sizeOrGrade?: string,
        public qtyOrMc?: number,
        public qtyOrKgs?: number,
        public rateOrKg?: number,
        public currencyType?: CommercialCurrency,
        public total?: number,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public commercialPurchaseOrderPurchaseOrderNo?: string,
        public commercialPurchaseOrderId?: number
    ) {}
}
