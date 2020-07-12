import { Moment } from 'moment';

export interface ISupplyShop {
    id?: number;
    name?: string;
    contact?: string;
    email?: string;
    address?: string;
    additionalInformation?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyZoneName?: string;
    supplyZoneId?: number;
    supplyAreaName?: string;
    supplyAreaId?: number;
    supplyZoneManagerId?: number;
    supplyAreaManagerId?: number;
    supplySalesRepresentativeName?: string;
    supplySalesRepresentativeId?: number;
}

export class SupplyShop implements ISupplyShop {
    constructor(
        public id?: number,
        public name?: string,
        public contact?: string,
        public email?: string,
        public address?: string,
        public additionalInformation?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyZoneName?: string,
        public supplyZoneId?: number,
        public supplyAreaName?: string,
        public supplyAreaId?: number,
        public supplyZoneManagerId?: number,
        public supplyAreaManagerId?: number,
        public supplySalesRepresentativeName?: string,
        public supplySalesRepresentativeId?: number
    ) {}
}
