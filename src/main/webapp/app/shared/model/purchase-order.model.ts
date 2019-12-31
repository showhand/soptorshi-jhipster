import { Moment } from 'moment';
import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';

export const enum PurchaseOrderStatus {
    WAITING_FOR_ACCOUNTS_APPROVAL = 'WAITING_FOR_ACCOUNTS_APPROVAL',
    APPROVED_BY_ACCOUNTS = 'APPROVED_BY_ACCOUNTS',
    REJECTED_BY_ACCOUNTS = 'REJECTED_BY_ACCOUNTS',
    MODIFICATION_REQUEST_BY_ACCOUNTS = 'MODIFICATION_REQUEST_BY_ACCOUNTS',
    WAITING_FOR_CFO_APPROVAL = 'WAITING_FOR_CFO_APPROVAL',
    APPROVED_BY_CFO = 'APPROVED_BY_CFO',
    REJECTED_BY_CFO = 'REJECTED_BY_CFO',
    MODIFICATION_REQUEST_BY_CFO = 'MODIFICATION_REQUEST_BY_CFO',
    CLOSED_BY_CFO = 'CLOSED_BY_CFO'
}

export interface IPurchaseOrder {
    id?: number;
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
    comments?: IPurchaseOrderMessages[];
    requisitionRequisitionNo?: string;
    requisitionId?: number;
    quotationQuotationNo?: string;
    quotationId?: number;
}

export class PurchaseOrder implements IPurchaseOrder {
    constructor(
        public id?: number,
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
        public comments?: IPurchaseOrderMessages[],
        public requisitionRequisitionNo?: string,
        public requisitionId?: number,
        public quotationQuotationNo?: string,
        public quotationId?: number
    ) {}
}
