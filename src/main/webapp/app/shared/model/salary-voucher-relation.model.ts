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

export interface ISalaryVoucherRelation {
    id?: number;
    year?: number;
    month?: MonthType;
    voucherNo?: string;
    modifiedBy?: string;
    modifiedOn?: Moment;
    officeName?: string;
    officeId?: number;
}

export class SalaryVoucherRelation implements ISalaryVoucherRelation {
    constructor(
        public id?: number,
        public year?: number,
        public month?: MonthType,
        public voucherNo?: string,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public officeName?: string,
        public officeId?: number
    ) {}
}
