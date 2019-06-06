import { Moment } from 'moment';

export const enum PaymentStatus {
    PAID = 'PAID',
    NOT_PAID = 'NOT_PAID'
}

export const enum FineFetchType {
    ALL_EMPLOYEE = 'ALL EMPLOYEES',
    EMPLOYEE_SPECIFIC = 'EMPLOYEE SPECIFIC'
}

export interface IFine {
    id?: number;
    amount?: number;
    fineDate?: Moment;
    monthlyPayable?: number;
    paymentStatus?: PaymentStatus;
    left?: number;
    modifiedBy?: string;
    modifiedDate?: Moment;
    reason?: any;
    employeeFullName?: string;
    employeeId?: number;
}

export class Fine implements IFine {
    constructor(
        public id?: number,
        public amount?: number,
        public fineDate?: Moment,
        public monthlyPayable?: number,
        public paymentStatus?: PaymentStatus,
        public left?: number,
        public modifiedBy?: string,
        public modifiedDate?: Moment,
        public reason?: any,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
