import { Moment } from 'moment';

export const enum CommercialPaymentCategory {
    UNDEFINED = 'UNDEFINED',
    TT_BASED = 'TT_BASED',
    LC_BASED = 'LC_BASED',
    CASH = 'CASH',
    CHEQUE = 'CHEQUE',
    PAY_ORDER = 'PAY_ORDER'
}

export const enum CommercialCurrency {
    BDT = 'BDT',
    US_DOLLAR = 'US_DOLLAR'
}

export interface ICommercialPaymentInfo {
    id?: number;
    paymentCategory?: CommercialPaymentCategory;
    totalAmount?: number;
    currencyType?: CommercialCurrency;
    paymentTerms?: string;
    createdBy?: string;
    createOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    commercialPurchaseOrderPurchaseOrderNo?: string;
    commercialPurchaseOrderId?: number;
}

export class CommercialPaymentInfo implements ICommercialPaymentInfo {
    constructor(
        public id?: number,
        public paymentCategory?: CommercialPaymentCategory,
        public totalAmount?: number,
        public currencyType?: CommercialCurrency,
        public paymentTerms?: string,
        public createdBy?: string,
        public createOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public commercialPurchaseOrderPurchaseOrderNo?: string,
        public commercialPurchaseOrderId?: number
    ) {}
}
