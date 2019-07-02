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

export const enum SelectionType {
    SELECTED = 'SELECTED',
    NOT_SELECTED = 'NOT_SELECTED'
}

export interface IQuotation {
    id?: number;
    quotationNo?: string;
    currency?: Currency;
    payType?: PayType;
    creditLimit?: number;
    vatStatus?: VatStatus;
    aitStatus?: AITStatus;
    warrantyStatus?: WarrantyStatus;
    loadingPort?: string;
    remarks?: any;
    attachmentContentType?: string;
    attachment?: any;
    selectionStatus?: SelectionType;
    modifiedBy?: string;
    modifiedOn?: Moment;
    requisitionRequisitionNo?: string;
    requisitionId?: number;
    vendorCompanyName?: string;
    vendorId?: number;
}

export class Quotation implements IQuotation {
    constructor(
        public id?: number,
        public quotationNo?: string,
        public currency?: Currency,
        public payType?: PayType,
        public creditLimit?: number,
        public vatStatus?: VatStatus,
        public aitStatus?: AITStatus,
        public warrantyStatus?: WarrantyStatus,
        public loadingPort?: string,
        public remarks?: any,
        public attachmentContentType?: string,
        public attachment?: any,
        public selectionStatus?: SelectionType,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public requisitionRequisitionNo?: string,
        public requisitionId?: number,
        public vendorCompanyName?: string,
        public vendorId?: number
    ) {}
}
