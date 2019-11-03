import { Moment } from 'moment';

export const enum CommercialCurrency {
    BDT = 'BDT',
    US_DOLLAR = 'US_DOLLAR'
}

export interface ICommercialWorkOrderDetails {
    id?: number;
    goods?: string;
    reason?: string;
    size?: string;
    color?: string;
    quantity?: number;
    currencyType?: CommercialCurrency;
    rate?: number;
    createdBy?: string;
    createOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    commercialWorkOrderRefNo?: string;
    commercialWorkOrderId?: number;
}

export class CommercialWorkOrderDetails implements ICommercialWorkOrderDetails {
    constructor(
        public id?: number,
        public goods?: string,
        public reason?: string,
        public size?: string,
        public color?: string,
        public quantity?: number,
        public currencyType?: CommercialCurrency,
        public rate?: number,
        public createdBy?: string,
        public createOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public commercialWorkOrderRefNo?: string,
        public commercialWorkOrderId?: number
    ) {}
}
