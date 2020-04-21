import { Moment } from 'moment';

export const enum UnitOfMeasurements {
    PCS = 'PCS',
    KG = 'KG',
    TON = 'TON',
    GRAM = 'GRAM'
}

export interface IRequisitionDetails {
    id?: number;
    requiredOn?: Moment;
    estimatedDate?: Moment;
    uom?: UnitOfMeasurements;
    unit?: number;
    unitPrice?: number;
    quantity?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    productCategoryName?: string;
    productCategoryId?: number;
    requisitionRequisitionNo?: string;
    requisitionId?: number;
    productName?: string;
    productId?: number;
}

export class RequisitionDetails implements IRequisitionDetails {
    constructor(
        public id?: number,
        public requiredOn?: Moment,
        public estimatedDate?: Moment,
        public uom?: UnitOfMeasurements,
        public unit?: number,
        public unitPrice?: number,
        public quantity?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public productCategoryName?: string,
        public productCategoryId?: number,
        public requisitionRequisitionNo?: string,
        public requisitionId?: number,
        public productName?: string,
        public productId?: number
    ) {}
}
