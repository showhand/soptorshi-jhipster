import { Moment } from 'moment';

export const enum PaymentStatus {
    PAID = 'PAID',
    NOT_PAID = 'NOT_PAID'
}

export interface ILoan {
    id?: number;
    amount?: number;
    takenOn?: Moment;
    monthlyPayable?: number;
    paymentStatus?: PaymentStatus;
    left?: number;
    modifiedBy?: number;
    modifiedDate?: Moment;
    employeeId?: number;
}

export class Loan implements ILoan {
    constructor(
        public id?: number,
        public amount?: number,
        public takenOn?: Moment,
        public monthlyPayable?: number,
        public paymentStatus?: PaymentStatus,
        public left?: number,
        public modifiedBy?: number,
        public modifiedDate?: Moment,
        public employeeId?: number
    ) {}
}
