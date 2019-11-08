import { Moment } from 'moment';

export interface ISupplySalesRepresentative {
    id?: number;
    salesRepresentativeName?: string;
    additionalInformation?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyZoneZoneName?: string;
    supplyZoneId?: number;
    supplyAreaAreaName?: string;
    supplyAreaId?: number;
    supplyAreaManagerManagerName?: string;
    supplyAreaManagerId?: number;
}

export class SupplySalesRepresentative implements ISupplySalesRepresentative {
    constructor(
        public id?: number,
        public salesRepresentativeName?: string,
        public additionalInformation?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyZoneZoneName?: string,
        public supplyZoneId?: number,
        public supplyAreaAreaName?: string,
        public supplyAreaId?: number,
        public supplyAreaManagerManagerName?: string,
        public supplyAreaManagerId?: number
    ) {}
}
