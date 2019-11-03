import { Moment } from 'moment';

export interface ICommercialWorkOrder {
    id?: number;
    refNo?: string;
    workOrderDate?: Moment;
    deliveryDate?: Moment;
    remarks?: string;
    createdBy?: string;
    createOn?: Moment;
    updatedBy?: string;
    updatedOn?: string;
    commercialPurchaseOrderPurchaseOrderNo?: string;
    commercialPurchaseOrderId?: number;
}

export class CommercialWorkOrder implements ICommercialWorkOrder {
    constructor(
        public id?: number,
        public refNo?: string,
        public workOrderDate?: Moment,
        public deliveryDate?: Moment,
        public remarks?: string,
        public createdBy?: string,
        public createOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: string,
        public commercialPurchaseOrderPurchaseOrderNo?: string,
        public commercialPurchaseOrderId?: number
    ) {}
}
