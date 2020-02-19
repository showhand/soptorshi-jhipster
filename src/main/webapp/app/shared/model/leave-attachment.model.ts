import { Moment } from 'moment';

export interface ILeaveAttachment {
    id?: number;
    fileContentType?: string;
    file?: any;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    leaveApplicationId?: number;
}

export class LeaveAttachment implements ILeaveAttachment {
    constructor(
        public id?: number,
        public fileContentType?: string,
        public file?: any,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public leaveApplicationId?: number
    ) {}
}
