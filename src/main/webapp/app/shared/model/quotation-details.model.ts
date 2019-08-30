import { Moment } from 'moment';

export const enum Currency {
    TAKA = 'TAKA',
    DOLLAR = 'DOLLAR',
    EURO = 'EURO'
}

export const enum PayType {
    CASH = 'CASH',
    PAY_ORDER = 'PAY_ORDER',
    CHEQUE = 'CHEQUE'
}

export const enum VatStatus {
    EXCLUDED = 'EXCLUDED',
    INCLUDED = 'INCLUDED'
}

export const enum AITStatus {
    EXCLUDED = 'EXCLUDED',
    INCLUDED = 'INCLUDED'
}

export const enum WarrantyStatus {
    WARRANTY = 'WARRANTY',
    NO_WARRANTY = 'NO_WARRANTY'
}

export interface IQuotationDetails {
    id?: number;
    currency?: Currency;
    rate?: number;
    quantity?: number;
    payType?: PayType;
    creditLimit?: number;
    vatStatus?: VatStatus;
    aitStatus?: AITStatus;
    warrantyStatus?: WarrantyStatus;
    loadingPort?: string;
    remarks?: any;
    modifiedBy?: string;
    modifiedOn?: Moment;
    quotationQuotationNo?: string;
    quotationId?: number;
    requisitionDetailsId?: number;
    productName?: string;
    productId?: number;
}

export class QuotationDetails implements IQuotationDetails {
    constructor(
        public id?: number,
        public currency?: Currency,
        public rate?: number,
        public quantity?: number,
        public payType?: PayType,
        public creditLimit?: number,
        public vatStatus?: VatStatus,
        public aitStatus?: AITStatus,
        public warrantyStatus?: WarrantyStatus,
        public loadingPort?: string,
        public remarks?: any,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public quotationQuotationNo?: string,
        public quotationId?: number,
        public requisitionDetailsId?: number,
        public productName?: string,
        public productId?: number
    ) {}
}
