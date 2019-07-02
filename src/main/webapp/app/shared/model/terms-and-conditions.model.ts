import { Moment } from 'moment';

export interface ITermsAndConditions {
    id?: number;
    description?: any;
    modifiedBy?: string;
    modifiedOn?: Moment;
    purchaseOrderPurchaseOrderNo?: string;
    purchaseOrderId?: number;
}

export class TermsAndConditions implements ITermsAndConditions {
    constructor(
        public id?: number,
        public description?: any,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public purchaseOrderPurchaseOrderNo?: string,
        public purchaseOrderId?: number
    ) {}
}
