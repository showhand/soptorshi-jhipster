import { Moment } from 'moment';

export interface ISalary {
    id?: number;
    basic?: number;
    houseRent?: number;
    medicalAllowance?: number;
    incrementRate?: number;
    otherAllowance?: number;
    modifiedBy?: number;
    modifiedOn?: Moment;
    employeeId?: number;
}

export class Salary implements ISalary {
    constructor(
        public id?: number,
        public basic?: number,
        public houseRent?: number,
        public medicalAllowance?: number,
        public incrementRate?: number,
        public otherAllowance?: number,
        public modifiedBy?: number,
        public modifiedOn?: Moment,
        public employeeId?: number
    ) {}
}
