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

export interface IDepreciationCalculation {
    id?: number;
    monthType?: MonthType;
    isExecuted?: boolean;
    createdBy?: string;
    createdOn?: Moment;
    modifiedBy?: string;
    modifiedOn?: Moment;
    financialAccountYearId?: number;
}

export class DepreciationCalculation implements IDepreciationCalculation {
    constructor(
        public id?: number,
        public monthType?: MonthType,
        public isExecuted?: boolean,
        public createdBy?: string,
        public createdOn?: Moment,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public financialAccountYearId?: number
    ) {
        this.isExecuted = this.isExecuted || false;
    }
}
