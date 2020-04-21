import { Moment } from 'moment';
import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';

export const enum RequisitionType {
    NORMAL = 'NORMAL',
    SUPPLY_CHAIN = 'SUPPLY_CHAIN',
    COMMERCIAL = 'COMMERCIAL'
}

export const enum RequisitionStatus {
    WAITING_FOR_HEADS_APPROVAL = 'WAITING_FOR_HEADS_APPROVAL',
    REJECTED_BY_HEAD = 'REJECTED_BY_HEAD',
    FORWARDED_BY_HEAD = 'FORWARDED_BY_HEAD',
    FORWARDED_BY_PURCHASE_COMMITTEE = 'FORWARDED_BY_PURCHASE_COMMITTEE',
    REJECTED_BY_PURCHASE_COMMITTEE = 'REJECTED_BY_PURCHASE_COMMITTEE',
    MODIFICATION_REQUEST_BY_PURCHASE_COMMITTEE = 'MODIFICATION_REQUEST_BY_PURCHASE_COMMITTEE',
    APPROVED_BY_CFO = 'APPROVED_BY_CFO',
    REJECTED_BY_CFO = 'REJECTED_BY_CFO',
    MODIFICATION_REQUEST_BY_CFO = 'MODIFICATION_REQUEST_BY_CFO',
    RECEIVED_BY_REQUISIONER = 'RECEIVED_BY_REQUISIONER',
    RECEIVED_VERIFIED_BY_HEAD = 'RECEIVED_VERIFIED_BY_HEAD',
    CLOSED_BY_CFO = 'CLOSED_BY_CFO'
}

export interface IRequisition {
    id?: number;
    requisitionNo?: string;
    requisitionType?: RequisitionType;
    reason?: any;
    requisitionDate?: Moment;
    amount?: number;
    status?: RequisitionStatus;
    selected?: boolean;
    headRemarks?: any;
    refToHead?: number;
    purchaseCommitteeRemarks?: any;
    refToPurchaseCommittee?: number;
    cfoRemarks?: any;
    refToCfo?: number;
    commercialId?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    comments?: IRequisitionMessages[];
    employeeFullName?: string;
    employeeId?: number;
    officeName?: string;
    officeId?: number;
    productCategoryName?: string;
    productCategoryId?: number;
    departmentName?: string;
    departmentId?: number;
}

export class Requisition implements IRequisition {
    constructor(
        public id?: number,
        public requisitionNo?: string,
        public requisitionType?: RequisitionType,
        public reason?: any,
        public requisitionDate?: Moment,
        public amount?: number,
        public status?: RequisitionStatus,
        public selected?: boolean,
        public headRemarks?: any,
        public refToHead?: number,
        public purchaseCommitteeRemarks?: any,
        public refToPurchaseCommittee?: number,
        public cfoRemarks?: any,
        public refToCfo?: number,
        public commercialId?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public comments?: IRequisitionMessages[],
        public employeeFullName?: string,
        public employeeId?: number,
        public officeName?: string,
        public officeId?: number,
        public productCategoryName?: string,
        public productCategoryId?: number,
        public departmentName?: string,
        public departmentId?: number
    ) {
        this.selected = this.selected || false;
    }
}
