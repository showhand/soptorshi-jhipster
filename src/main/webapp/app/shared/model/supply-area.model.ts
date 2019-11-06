import { Moment } from 'moment';

export interface ISupplyArea {
    id?: number;
    areaName?: string;
    areaCode?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyZoneZoneName?: string;
    supplyZoneId?: number;
}

export class SupplyArea implements ISupplyArea {
    constructor(
        public id?: number,
        public areaName?: string,
        public areaCode?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyZoneZoneName?: string,
        public supplyZoneId?: number
    ) {}
}
