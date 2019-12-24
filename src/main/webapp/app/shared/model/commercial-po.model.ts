import { Moment } from 'moment';

export const enum CommercialPoStatus {
    PO_CREATED = 'PO_CREATED',
    PREPARING_ORDER = 'PREPARING_ORDER',
    PRODUCT_DELIVERED = 'PRODUCT_DELIVERED',
    ORDER_CLOSED = 'ORDER_CLOSED'
}

export interface ICommercialPo {
    id?: number;
    purchaseOrderNo?: string;
    purchaseOrderDate?: Moment;
    originOfGoods?: string;
    finalDestination?: string;
    shipmentDate?: Moment;
    procurementId?: string;
    inventoryId?: string;
    poStatus?: CommercialPoStatus;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    commercialPiProformaNo?: string;
    commercialPiId?: number;
}

export class CommercialPo implements ICommercialPo {
    constructor(
        public id?: number,
        public purchaseOrderNo?: string,
        public purchaseOrderDate?: Moment,
        public originOfGoods?: string,
        public finalDestination?: string,
        public shipmentDate?: Moment,
        public procurementId?: string,
        public inventoryId?: string,
        public poStatus?: CommercialPoStatus,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public commercialPiProformaNo?: string,
        public commercialPiId?: number
    ) {}
}
