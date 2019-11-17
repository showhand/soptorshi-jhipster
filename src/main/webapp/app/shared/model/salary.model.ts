import { Moment } from 'moment';

export const enum SalaryStatus {
    ACTIVE = 'ACTIVE',
    NOT_ACTIVE = 'NOT_ACTIVE'
}

export interface ISalary {
    id?: number;
    basic?: number;
    gross?: number;
    startedOn?: Moment;
    endedOn?: Moment;
    salaryStatus?: SalaryStatus;
    modifiedBy?: string;
    modifiedOn?: Moment;
    employeeFullName?: string;
    employeeId?: number;
}

export class Salary implements ISalary {
    constructor(
        public id?: number,
        public basic?: number,
        public gross?: number,
        public startedOn?: Moment,
        public endedOn?: Moment,
        public salaryStatus?: SalaryStatus,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
