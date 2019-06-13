import { Moment } from 'moment';

export interface IManager {
    id?: number;
    parentEmployeeId?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    employeeFullName?: string;
    employeeId?: number;
}

export class Manager implements IManager {
    constructor(
        public id?: number,
        public parentEmployeeId?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
