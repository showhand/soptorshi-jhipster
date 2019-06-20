export interface ILeaveBalance {
    employeeId?: string;
    remainingDays?: number;
    leaveTypeId?: string;
    leaveTypeName?: string;
}

export class LeaveBalance implements ILeaveBalance {
    constructor(public employeeId?: string, public remainingDays?: number, public leaveTypeId?: string, public leaveTypeName?: string) {}
}
