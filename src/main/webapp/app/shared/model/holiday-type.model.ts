import { Moment } from 'moment';

export interface IHolidayType {
    id?: number;
    name?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
}

export class HolidayType implements IHolidayType {
    constructor(
        public id?: number,
        public name?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment
    ) {}
}
