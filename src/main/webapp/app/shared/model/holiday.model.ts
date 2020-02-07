import { Moment } from 'moment';

export const enum YesOrNo {
    YES = 'YES',
    NO = 'NO'
}

export const enum HolidayImposedAuthority {
    GOVERNMENT = 'GOVERNMENT',
    ORGANIZATIONAL = 'ORGANIZATIONAL',
    COMBINED = 'COMBINED',
    OTHERS = 'OTHERS'
}

export interface IHoliday {
    id?: number;
    fromDate?: Moment;
    toDate?: Moment;
    numberOfDays?: number;
    moonDependency?: YesOrNo;
    holidayDeclaredBy?: HolidayImposedAuthority;
    remarks?: any;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    holidayYear?: number;
    holidayTypeName?: string;
    holidayTypeId?: number;
}

export class Holiday implements IHoliday {
    constructor(
        public id?: number,
        public fromDate?: Moment,
        public toDate?: Moment,
        public numberOfDays?: number,
        public moonDependency?: YesOrNo,
        public holidayDeclaredBy?: HolidayImposedAuthority,
        public remarks?: any,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public holidayYear?: number,
        public holidayTypeName?: string,
        public holidayTypeId?: number
    ) {}
}
