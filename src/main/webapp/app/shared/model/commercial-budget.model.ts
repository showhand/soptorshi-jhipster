import { Moment } from 'moment';

export const enum CommercialOrderCategory {
    LOCAL = 'LOCAL',
    FOREIGN = 'FOREIGN'
}

export const enum CommercialCustomerCategory {
    ZONE = 'ZONE',
    FOREIGN = 'FOREIGN'
}

export const enum PaymentType {
    LC = 'LC',
    TT = 'TT',
    CASH = 'CASH',
    CHEQUE = 'CHEQUE',
    OTHERS = 'OTHERS'
}

export const enum TransportType {
    CFR = 'CFR',
    CIF = 'CIF',
    FOB = 'FOB'
}

export const enum CommercialBudgetStatus {
    SAVE_AS_DRAFT = 'SAVE_AS_DRAFT',
    WAITING_FOR_APPROVAL = 'WAITING_FOR_APPROVAL',
    APPROVED = 'APPROVED',
    REJECTED = 'REJECTED'
}

export interface ICommercialBudget {
    id?: number;
    budgetNo?: string;
    type?: CommercialOrderCategory;
    customer?: CommercialCustomerCategory;
    budgetDate?: Moment;
    companyName?: string;
    paymentType?: PaymentType;
    transportationType?: TransportType;
    seaPortName?: string;
    seaPortCost?: number;
    airPortName?: string;
    airPortCost?: number;
    landPortName?: string;
    landPortCost?: number;
    insurancePrice?: number;
    totalTransportationCost?: number;
    totalQuantity?: number;
    totalOfferedPrice?: number;
    totalBuyingPrice?: number;
    profitAmount?: number;
    profitPercentage?: number;
    budgetStatus?: CommercialBudgetStatus;
    proformaNo?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
}

export class CommercialBudget implements ICommercialBudget {
    constructor(
        public id?: number,
        public budgetNo?: string,
        public type?: CommercialOrderCategory,
        public customer?: CommercialCustomerCategory,
        public budgetDate?: Moment,
        public companyName?: string,
        public paymentType?: PaymentType,
        public transportationType?: TransportType,
        public seaPortName?: string,
        public seaPortCost?: number,
        public airPortName?: string,
        public airPortCost?: number,
        public landPortName?: string,
        public landPortCost?: number,
        public insurancePrice?: number,
        public totalTransportationCost?: number,
        public totalQuantity?: number,
        public totalOfferedPrice?: number,
        public totalBuyingPrice?: number,
        public profitAmount?: number,
        public profitPercentage?: number,
        public budgetStatus?: CommercialBudgetStatus,
        public proformaNo?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment
    ) {}
}
