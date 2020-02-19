import { Moment } from 'moment';

export const enum PaidOrUnPaid {
    PAID = 'PAID',
    UNPAID = 'UNPAID',
    UNDECLARED = 'UNDECLARED'
}

export interface ILeaveType {
    id?: number;
    name?: string;
    paidLeave?: PaidOrUnPaid;
    maximumNumberOfDays?: number;
    description?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
}

export class LeaveType implements ILeaveType {
    constructor(
        public id?: number,
        public name?: string,
        public paidLeave?: PaidOrUnPaid,
        public maximumNumberOfDays?: number,
        public description?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment
    ) {}
}
