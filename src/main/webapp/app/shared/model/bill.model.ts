import { Moment } from 'moment';

export const enum BillCategory {
    PAYABLE = 'PAYABLE',
    RECEIVABLE = 'RECEIVABLE'
}

export interface IBill {
    id?: number;
    amount?: number;
    billCategory?: BillCategory;
    reason?: any;
    modifiedBy?: string;
    modifiedDate?: Moment;
    employeeFullName?: string;
    employeeId?: number;
}

export class Bill implements IBill {
    constructor(
        public id?: number,
        public amount?: number,
        public billCategory?: BillCategory,
        public reason?: any,
        public modifiedBy?: string,
        public modifiedDate?: Moment,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
