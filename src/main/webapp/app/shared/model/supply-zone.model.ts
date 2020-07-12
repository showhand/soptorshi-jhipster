import { Moment } from 'moment';

export interface ISupplyZone {
    id?: number;
    name?: string;
    code?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
}

export class SupplyZone implements ISupplyZone {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment
    ) {}
}
