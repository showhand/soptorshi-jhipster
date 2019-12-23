import { Moment } from 'moment';

export interface ICommercialPackaging {
    id?: number;
    consignmentNo?: string;
    consignmentDate?: Moment;
    brand?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    commercialPurchaseOrderPurchaseOrderNo?: string;
    commercialPurchaseOrderId?: number;
}

export class CommercialPackaging implements ICommercialPackaging {
    constructor(
        public id?: number,
        public consignmentNo?: string,
        public consignmentDate?: Moment,
        public brand?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public commercialPurchaseOrderPurchaseOrderNo?: string,
        public commercialPurchaseOrderId?: number
    ) {}
}
