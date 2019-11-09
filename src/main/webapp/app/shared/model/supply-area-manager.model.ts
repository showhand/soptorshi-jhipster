import { Moment } from 'moment';

export interface ISupplyAreaManager {
    id?: number;
    managerName?: string;
    additionalInformation?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyZoneZoneName?: string;
    supplyZoneId?: number;
    supplyAreaAreaName?: string;
    supplyAreaId?: number;
}

export class SupplyAreaManager implements ISupplyAreaManager {
    constructor(
        public id?: number,
        public managerName?: string,
        public additionalInformation?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyZoneZoneName?: string,
        public supplyZoneId?: number,
        public supplyAreaAreaName?: string,
        public supplyAreaId?: number
    ) {}
}
