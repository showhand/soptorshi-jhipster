import { Moment } from 'moment';

export const enum LeaveStatus {
    WAITING = 'WAITING',
    ACCEPTED = 'ACCEPTED',
    REJECTED = 'REJECTED'
}

export interface ILeaveApplication {
    id?: number;
    employeeId?: string;
    fromDate?: Moment;
    toDate?: Moment;
    numberOfDays?: number;
    reason?: string;
    appliedBy?: string;
    appliedOn?: Moment;
    actionTakenBy?: string;
    actionTakenOn?: Moment;
    status?: LeaveStatus;
    leaveTypesName?: string;
    leaveTypesId?: number;
}

export class LeaveApplication implements ILeaveApplication {
    constructor(
        public id?: number,
        public employeeId?: string,
        public fromDate?: Moment,
        public toDate?: Moment,
        public numberOfDays?: number,
        public reason?: string,
        public appliedBy?: string,
        public appliedOn?: Moment,
        public actionTakenBy?: string,
        public actionTakenOn?: Moment,
        public status?: LeaveStatus,
        public leaveTypesName?: string,
        public leaveTypesId?: number
    ) {}
}
