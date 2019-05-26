import { Moment } from 'moment';

export const enum PaymentStatus {
    PAID = 'PAID',
    NOT_PAID = 'NOT_PAID'
}

export interface IAdvance {
    id?: number;
    amount?: number;
    reasonContentType?: string;
    reason?: any;
    providedOn?: Moment;
    paymentStatus?: PaymentStatus;
    left?: number;
    modifiedBy?: number;
    modifiedOn?: Moment;
    employeeId?: number;
}

export class Advance implements IAdvance {
    constructor(
        public id?: number,
        public amount?: number,
        public reasonContentType?: string,
        public reason?: any,
        public providedOn?: Moment,
        public paymentStatus?: PaymentStatus,
        public left?: number,
        public modifiedBy?: number,
        public modifiedOn?: Moment,
        public employeeId?: number
    ) {}
}
