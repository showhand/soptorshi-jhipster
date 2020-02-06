import { Moment } from 'moment';

export const enum DayOfWeek {
    SUNDAY = 'SUNDAY',
    MONDAY = 'MONDAY',
    TUESDAY = 'TUESDAY',
    WEDNESDAY = 'WEDNESDAY',
    THURSDAY = 'THURSDAY',
    FRIDAY = 'FRIDAY',
    SATURDAY = 'SATURDAY'
}

export const enum WeekendStatus {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE'
}

export interface IWeekend {
    id?: number;
    numberOfDays?: number;
    activeFrom?: Moment;
    activeTo?: Moment;
    day1?: DayOfWeek;
    day2?: DayOfWeek;
    day3?: DayOfWeek;
    status?: WeekendStatus;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
}

export class Weekend implements IWeekend {
    constructor(
        public id?: number,
        public numberOfDays?: number,
        public activeFrom?: Moment,
        public activeTo?: Moment,
        public day1?: DayOfWeek,
        public day2?: DayOfWeek,
        public day3?: DayOfWeek,
        public status?: WeekendStatus,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment
    ) {}
}
