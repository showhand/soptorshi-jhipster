import { Moment } from 'moment';

export const enum PaidOrUnPaid {
    PAID = 'PAID',
    UNPAID = 'UNPAID',
    UNDECLARED = 'UNDECLARED'
}

export const enum LeaveStatus {
    WAITING = 'WAITING',
    ACCEPTED = 'ACCEPTED',
    REJECTED = 'REJECTED'
}

export interface ILeaveApplication {
    id?: number;
    fromDate?: Moment;
    toDate?: Moment;
    numberOfDays?: number;
    paidLeave?: PaidOrUnPaid;
    reason?: string;
    appliedOn?: Moment;
    actionTakenOn?: Moment;
    status?: LeaveStatus;
    leaveTypesName?: string;
    leaveTypesId?: number;
    employeesFullName?: string;
    employeesId?: number;
    appliedByIdFullName?: string;
    appliedByIdId?: number;
    actionTakenByIdFullName?: string;
    actionTakenByIdId?: number;
}

export class LeaveApplication implements ILeaveApplication {
    constructor(
        public id?: number,
        public fromDate?: Moment,
        public toDate?: Moment,
        public numberOfDays?: number,
        public paidLeave?: PaidOrUnPaid,
        public reason?: string,
        public appliedOn?: Moment,
        public actionTakenOn?: Moment,
        public status?: LeaveStatus,
        public leaveTypesName?: string,
        public leaveTypesId?: number,
        public employeesFullName?: string,
        public employeesId?: number,
        public appliedByIdFullName?: string,
        public appliedByIdId?: number,
        public actionTakenByIdFullName?: string,
        public actionTakenByIdId?: number
    ) {}
}
