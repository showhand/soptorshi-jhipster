import { Moment } from 'moment';

export const enum RequisitionStatus {
    REJECTED_BY_HEAD = 'REJECTED_BY_HEAD',
    FORWARDED_BY_HEAD = 'FORWARDED_BY_HEAD',
    FORWARDED_BY_PURCHASE_COMMITTEE = 'FORWARDED_BY_PURCHASE_COMMITTEE',
    REJECTED_BY_PURCHASE_COMMITTEE = 'REJECTED_BY_PURCHASE_COMMITTEE',
    CORRECTION_REQUEST_BY_PURCHASE_COMMITTEE = 'CORRECTION_REQUEST_BY_PURCHASE_COMMITTEE',
    APPROVED_BY_CFO = 'APPROVED_BY_CFO',
    REJECTED_BY_CFO = 'REJECTED_BY_CFO',
    CORRECTION_REQUEST_BY_CFO = 'CORRECTION_REQUEST_BY_CFO',
    CLOSED_BY_CFO = 'CLOSED_BY_CFO'
}

export interface IRequisition {
    id?: number;
    requisitionNo?: string;
    reason?: any;
    requisitionDate?: Moment;
    amount?: number;
    status?: RequisitionStatus;
    modifiedBy?: string;
    modifiedOn?: Moment;
    employeeFullName?: string;
    employeeId?: number;
    officeName?: string;
    officeId?: number;
    departmentName?: string;
    departmentId?: number;
}

export class Requisition implements IRequisition {
    constructor(
        public id?: number,
        public requisitionNo?: string,
        public reason?: any,
        public requisitionDate?: Moment,
        public amount?: number,
        public status?: RequisitionStatus,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public employeeFullName?: string,
        public employeeId?: number,
        public officeName?: string,
        public officeId?: number,
        public departmentName?: string,
        public departmentId?: number
    ) {}
}
