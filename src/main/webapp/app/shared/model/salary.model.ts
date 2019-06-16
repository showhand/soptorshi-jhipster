import { Moment } from 'moment';

export const enum SalaryStatus {
    ACTIVE = 'ACTIVE',
    NOT_ACTIVE = 'NOT_ACTIVE'
}

export interface ISalary {
    id?: number;
    basic?: number;
    startedOn?: Moment;
    endedOn?: Moment;
    salaryStatus?: SalaryStatus;
    modifiedBy?: string;
    modifiedOn?: Moment;
    employeeId?: number;
}

export class Salary implements ISalary {
    constructor(
        public id?: number,
        public basic?: number,
        public startedOn?: Moment,
        public endedOn?: Moment,
        public salaryStatus?: SalaryStatus,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public employeeId?: number
    ) {}
}
