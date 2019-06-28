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
}

export class LeaveType implements ILeaveType {
    constructor(
        public id?: number,
        public name?: string,
        public paidLeave?: PaidOrUnPaid,
        public maximumNumberOfDays?: number,
        public description?: string
    ) {}
}
