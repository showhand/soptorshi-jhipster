import { Moment } from 'moment';

export interface ISupplyArea {
    id?: number;
    name?: string;
    code?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyZoneName?: string;
    supplyZoneId?: number;
    supplyZoneManagerId?: number;
}

export class SupplyArea implements ISupplyArea {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyZoneName?: string,
        public supplyZoneId?: number,
        public supplyZoneManagerId?: number
    ) {}
}
