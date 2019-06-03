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
    monthlyPayable?: number;
    paymentStatus?: PaymentStatus;
    left?: number;
    modifiedBy?: string;
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
        public monthlyPayable?: number,
        public paymentStatus?: PaymentStatus,
        public left?: number,
        public modifiedBy?: string,
        public modifiedDate?: Moment,
        public employeeId?: number
    ) {}
}
