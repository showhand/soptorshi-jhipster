import { Moment } from 'moment';

export const enum PurchaseOrderStatus {
    WAITING_FOR_CFO_APPROVAL = 'WAITING_FOR_CFO_APPROVAL',
    APPROVED_BY_CFO = 'APPROVED_BY_CFO',
    REJECTED_BY_CFO = 'REJECTED_BY_CFO',
    MODIFICATION_REQUEST_BY_CFO = 'MODIFICATION_REQUEST_BY_CFO',
    CLOSED_BY_CFO = 'CLOSED_BY_CFO'
}

export interface IPurchaseOrder {
    id?: number;
    purchaseOrderId?: number;
    purchaseOrderNo?: string;
    workOrderNo?: string;
    issueDate?: Moment;
    referredTo?: string;
    subject?: string;
    note?: any;
    laborOrOtherAmount?: number;
    discount?: number;
    status?: PurchaseOrderStatus;
    modifiedBy?: string;
    modifiedOn?: Moment;
    requisitionRequisitionNo?: string;
    requisitionId?: number;
    quotationQuotationNo?: string;
    quotationId?: number;
}

export class PurchaseOrder implements IPurchaseOrder {
    constructor(
        public id?: number,
        public purchaseOrderId?: number,
        public purchaseOrderNo?: string,
        public workOrderNo?: string,
        public issueDate?: Moment,
        public referredTo?: string,
        public subject?: string,
        public note?: any,
        public laborOrOtherAmount?: number,
        public discount?: number,
        public status?: PurchaseOrderStatus,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public requisitionRequisitionNo?: string,
        public requisitionId?: number,
        public quotationQuotationNo?: string,
        public quotationId?: number
    ) {}
}
