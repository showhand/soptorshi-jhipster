import { Moment } from 'moment';

export interface ISupplyZone {
    id?: number;
    zoneName?: string;
    zoneCode?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
}

export class SupplyZone implements ISupplyZone {
    constructor(
        public id?: number,
        public zoneName?: string,
        public zoneCode?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment
    ) {}
}
