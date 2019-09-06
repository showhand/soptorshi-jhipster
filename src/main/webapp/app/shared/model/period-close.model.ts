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

export const enum PeriodCloseFlag {
    OPEN = 'OPEN',
    CLOSE = 'CLOSE'
}

export interface IPeriodClose {
    id?: number;
    monthType?: MonthType;
    closeYear?: number;
    flag?: PeriodCloseFlag;
    modifiedBy?: string;
    modifiedOn?: Moment;
    financialAccountYearDurationStr?: string;
    financialAccountYearId?: number;
}

export class PeriodClose implements IPeriodClose {
    constructor(
        public id?: number,
        public monthType?: MonthType,
        public closeYear?: number,
        public flag?: PeriodCloseFlag,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public financialAccountYearDurationStr?: string,
        public financialAccountYearId?: number
    ) {}
}
