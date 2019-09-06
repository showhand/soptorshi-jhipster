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

export interface IMonthlyBalance {
    id?: number;
    monthType?: MonthType;
    totMonthDbBal?: number;
    totMonthCrBal?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    accountBalanceId?: number;
}

export class MonthlyBalance implements IMonthlyBalance {
    constructor(
        public id?: number,
        public monthType?: MonthType,
        public totMonthDbBal?: number,
        public totMonthCrBal?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public accountBalanceId?: number
    ) {}
}
