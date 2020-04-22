import { Moment } from 'moment';

export const enum AllowanceType {
    HOUSE_RENT = 'HOUSE_RENT',
    MEDICAL_ALLOWANCE = 'MEDICAL_ALLOWANCE',
    FESTIVAL_BONUS = 'FESTIVAL_BONUS',
    OVERTIME_ALLOWANCE = 'OVERTIME_ALLOWANCE',
    FOOD_ALLOWANCE = 'FOOD_ALLOWANCE',
    DRIVER_ALLOWANCE = 'DRIVER_ALLOWANCE',
    FUEL_LUB_ALLOWANCE = 'FUEL_LUB_ALLOWANCE',
    TRAVEL_ALLOWANCE = 'TRAVEL_ALLOWANCE',
    CONV_ALLOWANCE = 'CONV_ALLOWANCE',
    MOBILE_ALLOWANCE = 'MOBILE_ALLOWANCE',
    ARREAR_ALLOWANCE = 'ARREAR_ALLOWANCE',
    OTHER_ALLOWANCE = 'OTHER_ALLOWANCE'
}

export const enum Religion {
    ISLAM = 'ISLAM',
    HINDU = 'HINDU',
    BUDDHIST = 'BUDDHIST',
    CHRISTIANS = 'CHRISTIANS',
    OTHERS = 'OTHERS'
}

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

export interface ISpecialAllowanceTimeLine {
    id?: number;
    allowanceType?: AllowanceType;
    religion?: Religion;
    year?: number;
    month?: MonthType;
    modifiedBy?: string;
    modifiedOn?: Moment;
}

export class SpecialAllowanceTimeLine implements ISpecialAllowanceTimeLine {
    constructor(
        public id?: number,
        public allowanceType?: AllowanceType,
        public religion?: Religion,
        public year?: number,
        public month?: MonthType,
        public modifiedBy?: string,
        public modifiedOn?: Moment
    ) {}
}
