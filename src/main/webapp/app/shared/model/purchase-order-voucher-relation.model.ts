import { Moment } from 'moment';

export interface IPurchaseOrderVoucherRelation {
    id?: number;
    voucherNo?: string;
    amount?: number;
    createBy?: string;
    modifiedBy?: string;
    modifiedOn?: Moment;
    voucherName?: string;
    voucherId?: number;
    purchaseOrderPurchaseOrderNo?: string;
    purchaseOrderId?: number;
}

export class PurchaseOrderVoucherRelation implements IPurchaseOrderVoucherRelation {
    constructor(
        public id?: number,
        public voucherNo?: string,
        public amount?: number,
        public createBy?: string,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public voucherName?: string,
        public voucherId?: number,
        public purchaseOrderPurchaseOrderNo?: string,
        public purchaseOrderId?: number
    ) {}
}
