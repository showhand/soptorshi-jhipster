export interface ILeaveBalance {
    employeeId?: string;
    totalLeaveApplicableDays?: number;
    remainingDays?: number;
    leaveTypeId?: string;
    leaveTypeName?: string;
}

export class LeaveBalance implements ILeaveBalance {
    constructor(
        public employeeId?: string,
        public totalLeaveApplicableDays?: number,
        public remainingDays?: number,
        public leaveTypeId?: string,
        public leaveTypeName?: string
    ) {}
}
