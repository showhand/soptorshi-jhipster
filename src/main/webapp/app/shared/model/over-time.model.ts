import { Moment } from 'moment';

export interface IOverTime {
    id?: number;
    overTimeDate?: Moment;
    fromTime?: Moment;
    toTime?: Moment;
    duration?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    employeeFullName?: string;
    employeeId?: number;
}

export class OverTime implements IOverTime {
    constructor(
        public id?: number,
        public overTimeDate?: Moment,
        public fromTime?: Moment,
        public toTime?: Moment,
        public duration?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
