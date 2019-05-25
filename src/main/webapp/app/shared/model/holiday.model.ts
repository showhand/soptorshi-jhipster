import { Moment } from 'moment';

export interface IHoliday {
    id?: number;
    fromDate?: Moment;
    toDate?: Moment;
    numberOfDays?: number;
    holidayTypeName?: string;
    holidayTypeId?: number;
}

export class Holiday implements IHoliday {
    constructor(
        public id?: number,
        public fromDate?: Moment,
        public toDate?: Moment,
        public numberOfDays?: number,
        public holidayTypeName?: string,
        public holidayTypeId?: number
    ) {}
}
