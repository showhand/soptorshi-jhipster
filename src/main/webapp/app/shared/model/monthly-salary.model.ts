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
    year?: number;
    month?: MonthType;
    basic?: number;
    gross?: number;
    houseRent?: number;
    medicalAllowance?: number;
    otherAllowance?: number;
    festivalAllowance?: number;
    absent?: number;
    fine?: number;
    advanceHO?: number;
    advanceFactory?: number;
    providentFund?: number;
    tax?: number;
    loanAmount?: number;
    billPayable?: number;
    billReceivable?: number;
    payable?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    employeeFullName?: string;
    employeeId?: number;
}

export class MonthlySalary implements IMonthlySalary {
    constructor(
        public id?: number,
        public year?: number,
        public month?: MonthType,
        public basic?: number,
        public gross?: number,
        public houseRent?: number,
        public medicalAllowance?: number,
        public otherAllowance?: number,
        public festivalAllowance?: number,
        public absent?: number,
        public fine?: number,
        public advanceHO?: number,
        public advanceFactory?: number,
        public providentFund?: number,
        public tax?: number,
        public loanAmount?: number,
        public billPayable?: number,
        public billReceivable?: number,
        public payable?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
