import { Moment } from 'moment';

export interface ICommercialPurchaseOrder {
    id?: number;
    purchaseOrderNo?: string;
    purchaseOrderDate?: Moment;
    originOfGoods?: string;
    finalDestination?: string;
    shipmentDate?: Moment;
    createdBy?: string;
    createOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
}

export class CommercialPurchaseOrder implements ICommercialPurchaseOrder {
    constructor(
        public id?: number,
        public purchaseOrderNo?: string,
        public purchaseOrderDate?: Moment,
        public originOfGoods?: string,
        public finalDestination?: string,
        public shipmentDate?: Moment,
        public createdBy?: string,
        public createOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment
    ) {}
}
