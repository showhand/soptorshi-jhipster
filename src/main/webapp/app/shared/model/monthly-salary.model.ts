import { Moment } from 'moment';

export const enum MonthType {
    JANUARY = 'JANUARY',
    FEBRUARY = 'FEBRUARY',
    MARCH = 'MARCH',
    APRIL = 'APRIL',
    MAY = 'MAY',
    JUNE = 'JUNE',
    JULY = 'JULY',
    AUGUST = 'AUGUST',
    SEPTEMBER = 'SEPTEMBER',
    OCTOBER = 'OCTOBER',
    NOVEMBER = 'NOVEMBER',
    DECEMBER = 'DECEMBER'
}

export interface IMonthlySalary {
    id?: number;
    month?: MonthType;
    basic?: number;
    houseRent?: number;
    medicalAllowance?: number;
    otherAllowance?: number;
    absent?: number;
    fine?: number;
    advanceHO?: number;
    advanceFactory?: number;
    providendFund?: number;
    tax?: number;
    loanAmount?: number;
    payable?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    employeeId?: number;
}

export class MonthlySalary implements IMonthlySalary {
    constructor(
        public id?: number,
        public month?: MonthType,
        public basic?: number,
        public houseRent?: number,
        public medicalAllowance?: number,
        public otherAllowance?: number,
        public absent?: number,
        public fine?: number,
        public advanceHO?: number,
        public advanceFactory?: number,
        public providendFund?: number,
        public tax?: number,
        public loanAmount?: number,
        public payable?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public employeeId?: number
    ) {}
}
