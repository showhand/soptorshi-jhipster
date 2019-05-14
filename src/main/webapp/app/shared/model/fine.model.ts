import { Moment } from 'moment';

export const enum PaymentStatus {
    PAID = 'PAID',
    NOT_PAID = 'NOT_PAID'
}

export interface IFine {
    id?: number;
    amount?: number;
    reasonContentType?: string;
    reason?: any;
    fineDate?: Moment;
    paymentStatus?: PaymentStatus;
    left?: number;
    modifiedBy?: number;
    modifiedDate?: Moment;
    employeeId?: number;
}

export class Fine implements IFine {
    constructor(
        public id?: number,
        public amount?: number,
        public reasonContentType?: string,
        public reason?: any,
        public fineDate?: Moment,
        public paymentStatus?: PaymentStatus,
        public left?: number,
        public modifiedBy?: number,
        public modifiedDate?: Moment,
        public employeeId?: number
    ) {}
}
