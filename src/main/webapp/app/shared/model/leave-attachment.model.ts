export interface ILeaveAttachment {
    id?: number;
    fileContentType?: string;
    file?: any;
    leaveApplicationId?: number;
}

export class LeaveAttachment implements ILeaveAttachment {
    constructor(public id?: number, public fileContentType?: string, public file?: any, public leaveApplicationId?: number) {}
}
